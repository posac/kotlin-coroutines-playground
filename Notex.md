# Coroutines examples
## Basic coroutine - raw one
Not recommended to use unless you are creating your custom libraries and want to avoid dependencies to kotlinx.coroutines

Suspend function:
- is getting additional parameter - continuation
- changing return type - If function is suspending it return COROUTINE_SUSPENDED key
- for each suspend function we have anonymous impl for continuation
  - continuation holds state (variables and labels each suspend func)
  - holds place where we should return
  - is responsible to re-trigger suspended function
- similar to callbacks but without callbacks
  
    
 



##  kotlinx.coroutines

### coroutine builders
### coroutine context
### Jobs, cancelations and exception handling
### Dispatchers

## Channels and Flows



Questions?:

when coroutines can switch thread??