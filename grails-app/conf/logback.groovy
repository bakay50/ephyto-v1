import grails.util.BuildSettings
import grails.util.Environment
import org.springframework.boot.logging.logback.ColorConverter
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter

import java.nio.charset.Charset

conversionRule 'clr', ColorConverter
conversionRule 'wex', WhitespaceThrowableProxyConverter

// See http://logback.qos.ch/manual/groovy.html for details on configuration

appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName('UTF-8')
        pattern = '%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} ' + // Date
                '%clr(%5p) ' + // Log level
                '%clr(---){faint} %clr([%15.15t]){faint} ' + // Thread
                '%clr(%-40.40logger{39}){cyan} %clr(:){faint} ' + // Logger
                '%m%n%wex' // Message
    }
}

logger('com.webbfontaine',DEBUG)

def logDir = System.getProperty('catalina.base')
def logFile = '/ephyto/ephyto.log'
appender('FILE', FileAppender) {
    file = logDir ? "${logDir}/logs${logFile}" : "build/logs/${logFile}"
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = "%level %logger - %msg%n"
    }
}
if (logDir) {
    logger('com.webbfontaine',DEBUG, ['FILE'], false)
}

def targetDir = BuildSettings.TARGET_DIR
if (Environment.isDevelopmentMode()) {
    logger('grails.app.taglib.com.webbfontaine',INFO, ['STDOUT'], false)
    logger('org.grails.plugin.gspresources',INFO, ['STDOUT'], false)
    logger('org.springframework',INFO, ['STDOUT'], false)
    appender("FULL_STACKTRACE", FileAppender) {
        file = logDir ? "${logDir}/logs/ephyto/stacktrace.log" : "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}
root(ERROR, ['STDOUT'])