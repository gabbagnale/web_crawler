Web crawler in java.
To execute run.sh script, mvn and docker commands are needed.
The script will run 
 - mvn clean install: this will generate the jar of the application
 - docker build -t web_crawler -f Dockerfile . this will generate the docker image starting from the jar
 - docker run -p 8080:8080 web_crawler this will run the previowsly builded image on the 8080 port

After this, go on page localhost:8080/home and type the url you want to crawl.
