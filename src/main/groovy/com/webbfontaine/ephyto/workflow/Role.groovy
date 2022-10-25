package com.webbfontaine.ephyto.workflow

/**
 * Copyrights 2002-2016 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 15/09/2017
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
enum Role {
    TRADER, DECLARANT, GOV_OFFICER,GOV_SENIOR_OFFICER, SUPER_ADMINISTRATOR, ADMINISTRATOR, GOV_SUPERVISOR

    String getAuthority() {
        return "ROLE_" + getRole()
    }

    String getRole() {
        return "EPHYTO_" + name()
    }
}