## Command Application Submodule

### Domain driven design project structure

``` 
com.bank.account.cmd.api
```
<strong>Entrypoint for commands</strong> <br>
contains:
 -  REST api's & controllers
 -  Commands
 -  DTO's

``` 
com.bank.account.cmd.domain  
```
contains:
-  Account aggregates
-  Event store repository

``` 
com.bank.account.cmd.infrastructure  
```
contains:
-  Command dispatcher
-  Event producer
-  Event sourcing handler
