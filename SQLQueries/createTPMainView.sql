create view taxpayer_main_view (customer_id, ssn, first_name, last_name, tax_base, tax_amount, calc_amount)
as
select CUSTOMER.CUSTOMER_ID, CUSTOMER.SSN, CUSTOMER.FIRST_NAME, CUSTOMER.LAST_NAME, CUSTOMER.TAX_BASE, CUSTOMER.TAX_AMOUNT,
CUSTOMER.CALC_AMOUNT from CUSTOMER;
