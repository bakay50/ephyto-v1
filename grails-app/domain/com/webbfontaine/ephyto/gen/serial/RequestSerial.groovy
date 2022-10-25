package com.webbfontaine.ephyto.gen.serial

class RequestSerial {

    Integer requestNumberSequence
    Integer requestYear

    static constraints = {
        //Sequence
        requestNumberSequence min: 1, unique: ['requestYear']
    }
    static mapping = {
        requestNumberSequence column: 'RQST_SEQ'
        requestYear column: 'RQST_YER'

        version false
    }


    @Override
    public String toString() {
        return "RequestSerial{" +
                "id=" + id +
                ", version=" + version +
                ", requestNumberSequence=" + requestNumberSequence +
                ", requestYear=" + requestYear +
                '}';
    }
}
