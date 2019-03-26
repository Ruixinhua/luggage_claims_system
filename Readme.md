# Workflow of luggage claim 

## Customer claim process

### Process

- Click Claim settlements tab, customer will enter write.html page.

- Customer need to fill in following information to claim a luggage

  - customer name: string type, size 50.
  - customer ID: string type, size 20
  - claim no: string, size 20
  - customer phone number: string type, size 20, need to add area code(e.g. +86)
  - customer email address: string type, size 50, used to receive the result 
  - billing address: string type, size 100
  - claim type: string type, size 100
    - flight no: string size 20
    - lost luggage: string size 80

  - details: string type(maybe some images or files), the reason about claim luggage lost, e.g. Luggage Loss Statement from Airport Checkpoint.

- After customer claim a luggage, the result will be sent to the customer email address  and a message will to sent to the customer's telephone to remind customer.
- If the claim is accecpted, the money will be paid to the billing address
- If the claim is rejected, the customer will be told the reason and maybe re-claim again with more sufficient reason.
- If the detail is not enough to provide the evidence of the luggage lost claim, more information needed. The customer will receive a mail contain a link to add more details.(in most case)

### Customer Information

> id, primary key, string
>
> name, not null, string
>
> phone number, not null, string

### Claim Details

> claim no, primary key, string
>
> id, foreign key, not null, string
>
> billing address, not null, string
>
> flight no, not null, string
>
> lost luggage, string
>
> employee id, foreign key, not null, string (The claim will be sent to this employee)
>
> details, string(undetermined)

## Employee process Claim

### Login/Register

- The employee click login button, then  he/she will enter next page to login or register
- If the employee want to register, he/she need to enter some information which match the information stored in the database of insurance compan. The information is including:
  - employee id, string type, it should match the id with the company given.
  - employee name, string type, it should be real name
  - employee nickname, string type, it should be unique and it can be used to login
  - email address, string type, it will be used to receive the validation information and each email address can only be registered for one employee.
  - telephone number, string type, size 20, need to add area code(e.g. +86)
  - password, string type.
  - re-enter password, string type.
- For login process, the following information is needed:
  - employee id or employee email address or employee nickname
  - employee password

### Process the claim

- After the employee login, he/she will enter his/her own employee page which contains the claim that need to be process by employee(the distribution of claim is undetermined)
- The employee click process, then he/she will see the details of customer(personal information will be hidden from employee). 
- The employee will need to choose accept(give a reason to company maybe), reject(give a reason to customer) or ask customer to provide more information.
- After the customer provide more information, the employee will need to check again.

### Employee Information

> employee id, primary key, string
>
> employee name, string
>
> employee nickname, unique, string
>
> employee email address, unique, string
>
> employee phone number
>
> employee password