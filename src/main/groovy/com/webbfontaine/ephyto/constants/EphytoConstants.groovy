package com.webbfontaine.ephyto.constants
/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye.
 * Date: 16/09/2016
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class EphytoConstants {

    static final String IM = "IM"
    static final String EX = "EX"
    static final String VR ="VR"  //"Vrac"
    static final String BG ="BG"  //"Sacs"


    static final NAMES = [IM: "Import", EX: "Export"]
    static final SAD_VALID_STATUSES=["ASSESSED","PAID","EXITED","TOTALLY EXITED"]

    static final String CORRECT_RESULT = "true"

    static final String DELETE = "Delete.label"
    static final String APPROVE = "Approve.label"
    static final String DELIVER = "Deliver.label"
    static final String SHOW="Show"
    static final String CREATE="Create"
    static final String REPLACE="Replace"
    static final String STATUS_SEARCH_FIELD="status"
    static final String LEVEL_USER_PROPERTY="level"
    static final String SEARCH_ACTION="search"
    static final String MODIFY = "Modify.label"
    static final String CREATE_EPHYTO = "Create.ephyto.label"
    static final String VIEW = "View.label"
    static final String TYPE_PHYTO = "Certificat"
    static final String TYPE_DOCSCERTIFICAT = "PCD"
    static final String EDIT = "EDIT"
    static final String NATURE_TRAITEMENT = "nature.traitement.label"
    static final FIELDS_FOR_UTF8 = ["userReference","otReference","ptReference","dockingPermissionRef","meansOfTransport","observations","consigneeNameAddress","commercialDescriptionGoods","botanicalName","harvest","season","packageMarks","phytosanitaryCertificateRef"]
    static final GOOD_FIELDS_FOR_UTF8 = ["netWeight","grossWeight","quantity"]
    static final APPROVE_FIELDS_FOR_UTF8 = ["meansOfTransport","consigneeNameAddress","phytosanitaryCertificateRef"]
    static final REJECT_QUERY_FIELDS_FOR_UTF8 = ["message"]
    static final SEARCH_APPLICATOR_CODE= "applicatorCode"
    static final FIELDS_SEARCH_FOR_UTF8 = ["requestNumber","disinfectionCertificateRef","dockingPermissionRef","phytosanitaryCertificateRef","userReference"]
    static final EXCEPTION_PRODUCT = ["FRUIT","BOIS sans TRAITEMENT","DIVERS sans TRAITEMENT"]
    static final ALLOW_CHECKING_TREATMENT= ["CAFE","CACAO","BOIS","DIVERS","FRUIT"]
    static final FORCE_CHECKING_TREATMENT= ["BOIS avec TRAITEMENT","DIVERS avec TRAITEMENT"]
    static final YES = "Yes"
    static final NO= "No"
    static final MANGUE= "MANGUE"
    static final APPROCH_SYSTEMIQUE = "Approche Systémique"
    static final PRODUCT_ALLOW_TREATMENT= ["BOIS","DIVERS","FRUIT"]
    static final PRODUCT_FRUIT_MENTION= "CERTIFICAT ETABLI POUR LES PAYS D'EUROPE ET NON DE LA FEDERATION DE RUSSIE. " +
            "AUCUNE REEXPORTATION N'EST AUTORISEE A PARTIR DU CERTIFICAT D'ORIGINE."
    static final PRODUCT_BOIS_MENTION= "BOIS SAINS ET EXEMPTS DE TOUS PARASITES ET INSECTES"
    static final PRODUCT_MANGUE_MENTION= "L’expédition couverte par le présent certificat phytosanitaire est conforme au règlement (UE) 2021/2285," +
            " annexe VII, point 61 choix d) : Constatation officielle que les fruits ont été soumis à " +
            "une approche systémique efficace ou à un traitement efficace après récolte pour garantir " +
            "l’absence des Tephritidae visés à l’annexe II, partie A, tableau 3, point 77, auxquels ces fruits sont réputés sensibles," +
            " et que l’utilisation d’une approche systémique ou les détails de la méthode de traitement sont " +
            "indiqués sur le certificat phytosanitaire. Les mesures ont été communiquées à l’UE le 23 janvier 2020."

    static final String FOLDER="reports"
   // static final String SUB_FOLDER="src/main/groovy/com/webbfontaine/ephyto/reports/"
    static final String SUB_FOLDER="reports/"

    //add for migration 3
    public static final EPHYTO_DOMAIN = "ephytoGen"
    public static final EPHYTO_PRINT="print"
    public static final EPHYTO_PRINT_DRAFT="printDraft"
    public static final EPHYTO_CREATE="create"
    public static final MODE_OF_TRANSPORT_AIR= "4"
    static final String MAX_RESULTS = 10

}
