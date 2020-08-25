# ICCBank OpenAPI SDK for Java

## Example
```java
public class Demo {

	public static void main(String[] args) {
		String appId = "xxx";
		String appSecret = "xxx";
		String token = "xxx";

		ApiClient client = new DefaultApiClient(appId, appSecret, token);

		ApiResponse<Object> res = client.addressCheck("LTC", "LYFvjx6EBdQr3uTdwUkBvRczwhKdiwGpeq");
		
		System.out.println(JsonUtils.toJsonString(res));
	}
}
```

## Maven users

Add this repository and dependency to your project's POM:

```xml
<repository>
    <id>openapi-sdk-java</id>
    <url>https://raw.github.com/iccbank/openapi-sdk-java/master/</url>
</repository>
```

```xml
<dependency>
  <groupId>net.iccbank</groupId>
  <artifactId>openapi-sdk-java</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "net.iccbank:openapi-sdk-java:1.0.0"
```

