create view input_event_view (ACCOUNT_TRANSACTION_SYS_ID, INPUT_TRANSACTION_TXT, ROWNUM)
as
select INPUT_TRANSACTION.ACCOUNT_TRANSACTION_SYS_ID, INPUT_TRANSACTION.INPUT_TRANSACTION_TXT,ROW_NUMBER() over () 
from ACCOUNT_TRANSACTION, INPUT_TRANSACTION where ACCOUNT_TRANSACTION.ACCOUNT_TRANSACTION_SYS_ID=INPUT_TRANSACTION.ACCOUNT_TRANSACTION_SYS_ID
order by ACCOUNT_TRANSACTION.ACCOUNT_TRANSACTION_DT