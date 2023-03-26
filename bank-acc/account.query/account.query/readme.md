## Query Application Submodule

### Domain driven design project structure

``` 
com.bank.account.query.api
```
<strong>Entrypoint for commands</strong> <br>
contains:
-  REST api's & controllers
-  Queries
-  DTO's

``` 
com.bank.account.query.domain  
```
contains:
-  Account repository
-  Bank account domain entity

``` 
com.bank.account.query.infrastructure  
```
contains:
-  Consumer implementation
-  Query dispatcher
