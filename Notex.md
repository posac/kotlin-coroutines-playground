# Coroutines examples

A coroutine is an instance of suspendable computation. 
It is conceptually similar to a thread, in the sense that it takes a block of code to run that works concurrently with the rest of the code.
However, a coroutine is not bound to any particular thread. It may suspend its execution in one thread and resume in another one.

## Basic coroutine - raw one

Suspend function:
- is getting additional parameter - continuation
- changing return type - If function is suspending it return COROUTINE_SUSPENDED key
- for each suspend function we have anonymous impl for continuation
  - continuation holds state (variables and labels each suspend func)
  - holds place where we should return
  - is responsible to re-trigger suspended function
- similar to callbacks but without callbacks

Not recommended to use unless you are creating your custom libraries and want to avoid dependencies to kotlinx.coroutines


##  kotlinx.coroutines

### coroutine builders
- launch - starts new coroutine - will be run independently. require parent coroutine (CoroutineScope)
- runBlocking - starts new coroutine but blocks thread. Should be not used too much - Recommended uses:
  - blocking main thread
  - test
- async - similar to launch - but designed to calculate values. Recommended usage:
  - calculate value parallel

### Structured concurrency
Each coroutine builders is extension function on CoroutineScope. Because of that starting new coroutine we have to be in some scope.
This is how we are creating coroutine tree.
What it is giving to us?
- children inherit context of parent
- when parent is canceled all children are canceled
- when child throws exception, parent and other children of parent can be destroyed  

### coroutineScope

coroutineScope is a suspending function that starts a scope. It re- turns a value produced by the argument function.

Unlike async or launch, the body of coroutineScope is called in-place. 
It formally creates a new coroutine, but it suspends a previous one until the new one is finished, so it does not start any concurrent process. 


The provided scope inherits its coroutineContext from the outer scope, but overrides the context’s Job. This way, the produced scope respects parental responsibilities:
• inherits a context from its parent,
• awaits for all children before it can finish itself,
• cancels all its children, when the parent is cancelled.

### coroutine context
CoroutineContext is an interface that represents an element or a collection
of elements. So CoroutineContext is just a way to hold and pass data. By default,
the parent passes its context to the child, which is one of the parentchild
relationship effects. We say that the child inherits context from
its parent.
``` defaultContext + parentContext + childContext ```


### Jobs
Conceptually, a job represents a cancellable thing with a lifecycle.
Formally, Job is an interface, but it has a concrete contract and state,
so it might be treated similarly to an abstract class.
A job lifecycle is represented by its state. Here is a graph of states and
the transitions between them
![img_1.png](img_1.png)

### Cancellations
A very important functionality of Kotlin Coroutines is cancellation.

The Job interface has a cancel method, which allows its cancellation.
Calling it triggers the following effects:
• Such a coroutine ends the job at the first suspension point
(delay in the example below).
• If a job has some children, they are also cancelled (but its
parent is not affected).
• Once a job is cancelled, it cannot be used as a parent for any
new coroutines. It is first in the “Cancelling” and then in the
“Cancelled” state.


### Exception handling


### Dispatchers

Defines what thread pool will execute our coroutine.
There is few predefined dispatchers:
- Default - default one - designed to run CPU - intensive operations. Thread pool size is equqal to the number of cores
- Main - mostly for android or other application framework - for example in Android there is only one thread that interacts with ui
- IO - designed to used when we block thread with IO operations, for instance blocking http calls. Limited to 64 or number of cores if is higher
- Unconfined - This dispatcher is different from the previous one, as it is not chang- ing a thread at all. When it is started, it runs on the thread on which it was started. If it is resumed, it runs on the thread that resumed it.
Dispatchers.Default and Dispatchers.IO share the same pool of threads. This is an important optimization. Threads are reused, and often redispatching is not needed.

For instance, lets say you are running on Dispatchers.Default, and then reach withContext(Dispatchers.IO) { ... }. 
You will stay on the same thread.
What will change, is that this thread will not count to the Dispatchers.Default limit, but instead to the Dispatchers.IO limit



## Channels

The typical cases where we use channels is when on one side values are produced, and on the other, we want to process them.

## Flows

It represents a stream of values that are being asynchronously computed, that supports coroutines (unlike sequences). 
There are quite a few use cases where Flow is useful


The most typical usages of the Flow include:
• Receiving messages, that are communicated through Server- Sent Events, WebSockets, notifications, etc.
• Observing user actions,like text changes or clicks.
• Receiving updates from sensors or other information about
device, like its localization or orientation.
• Observing changes on databases.
