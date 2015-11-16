###Advice Ripper
Create an application which gathers all advices from http://fucking-great-advice.ru/ 

####Features
   * retrieves all the advices from the site and store them in a large, properly formatted data file™, for quicker and more efficient learning;
   * server has to be polite, and won't knock the server down with frequent requests;
   * server is insisting, and makes sure that all the information is retrieved even if the server temporarily refuses to serve the client.

####Description
First of all I created a method `getAdvice(int id)` which gets the advice from the page, with a given id(number of the advice). Then I called it in different ways; and here is how this affected the execution time.

#####Test #1: Simple `for loop`
I created a for loop, and tried to print in console first 49 advices. Here is the output:
		
		Advice #1: Сохраняйся блять почаще!
		Advice #2: Продумай блять структуру!
		Advice #3: Используй блять ариал!
		Advice #4: Не используй блять ариал!
		Advice #5: Читай книги, ёпта!
		...
		Advice #49: Не тормози бля!
        *Execution time: 49.414457 seconds*
        Retry count: 0

As we can see, we get about 1 advice per second.

#####Test #2: `for loop` with Threads
I call `getAdvice(int id)` from a thread. Then inside a for loop, I create a thread for every id; So basically I create about 49 threads, and then wait for their response. Here is the result:

		Advice #48: Не тупи бля!
		Advice #35: Выбрось нахуй и начни заново!
		Advice #31: Учись блять писать тексты!
		Advice #16: Есть же цветовая схема, мудила!
		Advice #12: Добавь блять воздуха в макет!
		...
		Advice #24: Не ссы бля, сделай как хочется!
		*Execution time: 6.416084 seconds*
		Retry count: 19

As we see, the execution time is much better, but, the application retried to receive 19 advices from server. Which means that we did not get response 19 times. Also, the order is extremelly unexpected.

#####Test #3: `for loop` with Thread and timer delay.
I decided to delay the thread execution for every new thread that is created.

        requestDelayMs = 50*i;

Every 50ms, I create a thread and request an advice. Here is the result:

		Advice #1: Сохраняйся блять почаще!
		Advice #2: Продумай блять структуру!
		Advice #4: Не используй блять ариал!
		Advice #3: Используй блять ариал!
		Advice #5: Читай книги, ёпта!
		...
		Advice #48: Не тупи бля!
		*Execution time: 3.689554 seconds*
		Retry count: 0

Here we successfully got the best possible time, the order is pretty much as expected. If a request would be retried, we would get +1 second for execution time. I also I tried to create a new thread every 1ms and 25ms, but the result is the same as without delay, almost half of them are retried. 


####Conclusion
Multithreading is great, but if one uses it wrong, its efficiency may dramatically drop. Per general, I have understood how to efficiently request data from server in order not to spam the server, not to loose packets on the way, and of course not to waste time.








