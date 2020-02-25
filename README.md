## CRAWLO

A web crawler is a software program which browses the World Wide Web in a methodical and automated manner. It collects documents by recursively fetching links from a set of starting pages. Many sites, particularly search engines, use web crawling as a means of providing up-to-date data. 

## Getting Started
### Prerequisites
##### 1) Install Java 11

- First install Homebrew, if not installed:
`ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`

- Then run the following commands to install Java 11 through Homebrew:
    -   `brew update`
    -   `brew tap caskroom/cask`
    -   `brew cask install java11`
##### 2) Install maven
- `brew install maven`
##### 3) Download DynamoDB Local
- Download LocalDynamoDB jar from [here](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html)
##### 4) Download Kafka
- Download the latest stable Kafka version from [here](https://kafka.apache.org/downloads)
### Installing
##### 1) Clone the service repo:
- `git clone https://github.com/AmrGeneidy/webcrawler.git`
##### 2) Start Kafka & DynamoDB
In order to get everything in place, the following procedure is to be followed:
- ##### Run DynamoDB Local server:
    - Navigate to where the jar was downloaded.
    - Run the jar with `java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb`
    - Run `aws configure` to configure the aws cli by providing both the AWS credentials and operating region.
- ##### Run Zookeeper server and Kafka Server:
    - Navigate to where Kafka was downloaded and unzip it.
    - Run Zookeeper server: `./bin/zookeeper-server-start.sh ./config/zookeeper.properties`
    - Run Kafka server: `./bin/kafka-server-start.sh ./config/server.properties`

##### 3) Run the service
- `mvn clean install package`
- `java -Xmx100m -jar target/webcrawler-0.0.1-SNAPSHOT.jar com.webcrawler.WebCrawlerApplication`
## Architecture 
![Image of Webcrawler](https://user-images.githubusercontent.com/32603655/75213192-0c371680-5792-11ea-94d5-d9bb39648262.png)
## Technologies 
- Java11
- Spring boot
- Kafka
- DynamoDB
- Maven
