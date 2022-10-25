# **Guce ephyto SQL Scripts**
*Latest first.*

## Release 3.3.5
## [CIV-2274]
**DB Table-**
    **Datasource:**
        *GIM*
        
        INSERT INTO CI_GIM.SYSTEM_INFO (ID, APP_NAME, DURATION, MSG, MSG_FR, STATUS, VALID_FROM, VALID_TO)
        VALUES (1006, 'ephyto', 1500000, 'The HelpDesk can also be reached at the following numbers: 59 69 23 50 / 59 60 56 81 / 68 30 24 89',
                'Le service d''aide en ligne (HelpDesk) est également joignable aux numéros suivants : 59 69 23 50 / 59 60 56 81 / 68 30 24 89',
                'Valid',
                TO_TIMESTAMP('2020-11-19 09:36:29.948000', 'YYYY-MM-DD HH24:MI:SS.FF6'),
                TO_TIMESTAMP('2024-12-31 09:36:29.950000', 'YYYY-MM-DD HH24:MI:SS.FF6'));

## Release 3.3.5
### [CIV-2320](https://jira.webbfontaine.com/browse/CIV-2320) - [PHYTO] Increase Document Type size

    ALTER TABLE CERTIFICATE_ATTACHMENTS RENAME COLUMN DOC_TYP TO DOC_TYP_OLD;
    ALTER TABLE CERTIFICATE_ATTACHMENTS ADD DOC_TYP VARCHAR2(100 CHAR);
    UPDATE CERTIFICATE_ATTACHMENTS SET DOC_TYP = DOC_TYP_OLD;
    ALTER TABLE CERTIFICATE_ATTACHMENTS DROP COLUMN DOC_TYP_OLD;