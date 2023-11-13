# CrossServerStorage
A custom network system to share data between servers on a proxy server, without the use of MySQL, using sockets allowing for more tightly intergrated plugins and more stable data transfer

# WARNING API IS NOT YET FINISHED
however, the repo is live and if you wanted to give it a try you can acess the api with the below maven repo, if you do decide to try it out, please download the sourcecode and build the CSS.jar yourself, DO NOT SHADE this api into your plugin as it is designed as a standalone library, if you do shade this api it could lead to alot of errors and incompatibilities, please BUILD yourself until i get a spigot page ready with a download


## Usage/Examples

```xml

        <repository>
            <id>codersclub-repo</id>
            <url>https://repo.codersclub.app/repository/codersclub-repo/</url>
        </repository>

        <dependency>
            <groupId>me.zoon20x</groupId>
            <artifactId>CrossServerStorage</artifactId>
            <version>1.1</version>
            <scope>provided</scope>
        </dependency>
```
