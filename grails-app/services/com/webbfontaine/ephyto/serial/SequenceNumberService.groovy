package com.webbfontaine.ephyto.serial

import com.webbfontaine.ephyto.gen.EphytoGen
import com.webbfontaine.ephyto.gen.serial.RequestSerial
import grails.gorm.transactions.Transactional
import org.grails.datastore.mapping.query.api.Criteria
import org.joda.time.LocalDate
import org.slf4j.LoggerFactory

@Transactional
class SequenceNumberService {
    private static final LOGGER = LoggerFactory.getLogger(SequenceNumberService)
    private final static Set<Serializable> LOCK = Collections.synchronizedSet(new HashSet<String>(0x64))
    public static final String REQUEST_NUMBER_PREFIX = "PTD"

    def execQueryOnRequestSerial(y) {
        Criteria maxCriteria = RequestSerial.createCriteria()
        return maxCriteria.get {
            eq('requestYear', y)
            projections {
                max("requestNumberSequence")
            }
        } as int
    }


    def nextRequestNumber(EphytoGen ephytoGen) {
        Integer currentYear = new LocalDate().now().year
        ephytoGen.withNewTransaction {
            try {
                enterCriticalSection(currentYear)
                //find last sequence number
                //def requestSerialQuery = "select max(requestNumberSequence) from RequestSerial rs where rs.requestYear=:y"
                def requestNumberSequence
                Integer nextRequestNumberSequence
                RequestSerial requestSerial = RequestSerial.findByRequestYear(currentYear)
                LOGGER.error(" === Retrieve RequestSerial === " + requestSerial.toString() + " For Year $currentYear")

                if (!requestSerial) {
                    requestSerial = new RequestSerial(requestYear: currentYear, requestNumberSequence: 1).save(flush: true)
                }
                //requestNumberSequence = RequestSerial.executeQuery(requestSerialQuery, [y: currentYear])
                requestNumberSequence = execQueryOnRequestSerial(currentYear)

                ephytoGen.requestNumber = "${REQUEST_NUMBER_PREFIX}" + currentYear.toString() + requestNumberSequence?.toString().padLeft(6, "0")

                LOGGER.error(" === Generated Request Number === " + ephytoGen.requestNumber)
                ephytoGen.requestNumberSequence = requestSerial ? requestNumberSequence : nextRequestNumberSequence

                nextRequestNumberSequence = requestSerial ? requestNumberSequence + 1 : nextRequestNumberSequence + 1
                LOGGER.error(" === Next Request Number Sequence === " + nextRequestNumberSequence)

                requestSerial = new RequestSerial(requestYear: currentYear, requestNumberSequence: nextRequestNumberSequence)
                requestSerial.save(flush: true)

            } catch (Exception e) {
                LOGGER.error(" ===ERROR in generating RequestNumber=== " + e.getMessage())
                throw new Exception(e)
            }
        }
        leaveCriticalSection(currentYear)
    }

    private static void enterCriticalSection(Serializable key) {
        LOGGER.debug("in enterCriticalSection with key: ${key}")
        synchronized (LOCK) {
            LOGGER.debug("system is trying to perform an operation to the document with lock key {}", key)
            while (LOCK.contains(key)) {
                try {
                    LOCK.wait()
                } catch (InterruptedException ie) {
                    LOGGER.error("Synchronization error : {}", ie)
                }
            }
            LOCK.add(key)
        }
    }

    private static void leaveCriticalSection(Serializable key) {
        synchronized (LOCK) {
            LOGGER.debug("releasing lock to document with key {}", key)

            LOCK.remove(key)
            LOCK.notify()
        }
    }
}
