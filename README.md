# WebConn
An Android Library for HTTP get and post requests and responses

## How to use?
```java
WebRequest request = new WebRequest(0, url);
request.setOnWebResultListener(new OnWebRequestListener());
WebThread.handle(request);
```
