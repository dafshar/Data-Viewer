update PSAM_PUSA
set PSAM_PUSA.CUST_ID = (select num from TMP2 where tmp2.tmp1 = psam_pusa.serialno);