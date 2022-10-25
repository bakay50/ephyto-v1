import com.webbfontaine.ephyto.erimm.Applicator
import com.webbfontaine.ephyto.erimm.PackageCode
import com.webbfontaine.ephyto.erimm.Product

com {
    webbfontaine {
        grails {
            plugins {
                security {
                    active = true
                    auth = true
                    config = "Requestmap"
                    users {

                        trader {
                            roles = [
                                    'ephyto_trader'
                            ]
                            properties = [DEC: "ALL", TIN: 'ALL', officeAccess: 'ALL', DOC: 'ALL', ownerGroup: 'ALL']
                        }

                        declarant {
                            roles = [
                                    'ephyto_declarant'
                            ]
                            properties = [DEC: "ALL", TIN: 'ALL', officeAccess: 'ALL', DOC: 'ALL', ownerGroup: 'ALL']
                        }


                        govofficer {
                            roles = [
                                    'ephyto_gov_officer'
                            ]
                            properties = [DEC: "ALL", TIN: 'ALL', officeAccess: 'ALL', DOC: 'ALL', ownerGroup: 'ALL']
                        }

                        govsenior {
                            roles = [
                                    'ephyto_gov_senior_officer'
                            ]
                            properties = [DEC: "ALL", TDR: 'ALL', officeAccess: 'ALL', DOC: 'ALL', ownerGroup: 'ALL']
                        }

                        govSupervisor {
                            roles = [
                                    'ephyto_gov_supervisor'
                            ]
                            properties = [DEC: "ALL", TIN: 'ALL', officeAccess: 'ALL', DOC: 'ALL', ownerGroup: 'ALL']
                        }

                        admin {
                            roles = [
                                    'ephyto_administrator'
                            ]
                            properties = [DEC: "ALL", TDR: 'ALL', INS: 'ALL', DOC: 'ALL', ownerGroup: 'ALL']

                        }
                        superadministrator {
                            roles = ['ephyto_super_administrator']
                            properties = [DEC: "ALL", TDR: 'ALL', INS: 'ALL', DOC: 'ALL', ownerGroup: 'ALL']
                        }

                    }
                    ignore = ['/actuator/**']
                }
                paths {
                    ephytoGen {
                        roles = ['ephyto_trader', 'ephyto_declarant', 'ephyto_govofficer', 'ephyto_govsenior', 'ephyto_govSupervisor', 'ephyto_admin']
                    }
                }
                utils {
                    dateTimeConverter {
                        localDate = ["dd/MM/yyyy", "yyyy-MM-dd"]
                        dateTime = ["dd/MM/yyyy HH:mm:ss"]
                    }
                }
            }
        }
    }
}

com {
    webbfontaine {
        grails {
            plugins {
                rimm {
                    alias {

                        RIMM_APL {
                            bean = Applicator.class.name
                            key = 'code'
                            value = 'agreement'
                        }

                        RIMM_PDT {
                            bean = Product.class.name
                            key = 'code'
                            value = 'name'
                        }

                        RIMM_PCK_COD {
                            bean = PackageCode.class.name
                            key = 'code'
                            value = 'description'
                        }
                    }
                }
            }
        }
    }
}






