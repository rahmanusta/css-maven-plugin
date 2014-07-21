# CSS Minifier Maven Plugin

Combines and Minifies your CSS files

## Usage

```xml
<plugin>
<groupId>com.kodcu</groupId>
<artifactId>css-maven-plugin</artifactId>
<version>1.0.0</version>
<configuration>
	<inputPath>${basedir}/src/main/webapp/css/</inputPath>
	<styles>
		<style>vendor/bootstrap.css</style>
		<style>vendor/layout.css</style>
		<style>custom.css</js>
	</styles>
	<outputPath>${basedir}/src/main/webapp/css/script.css</outputPath>
</configuration>
</plugin>
```

## Requirements

Needs Java 8+

[![Analytics](https://ga-beacon.appspot.com/UA-52823012-1/css-maven-plugin/readme)](https://github.com/rahmanusta/css-maven-plugin)
