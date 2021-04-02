### Installation:
 1. Download Apache NiFi from the official site [Apache Nifi](https://nifi.apache.org/download.html)
 2. Unpack the content of the archive into /opt or some other directory
 3. Put the following files into the ./conf folder:
    - adx.properties
    - adx-package-validation.gvy
    - adx-row-validation.gvy
    - flow.xml.gz
 4. Replace the pre-configured nifi.properties in the same above-mentioned location.
 5. Replace params in adx.properties with your local params, namely:
    - adx.folder
    - adx.folder.failures
    - adx.folder.success
    - adx.web.baseURL
    - adx.web.startSessionURL
    - adx.web.saveToADxURL
    - adx.web.closeSessionURL
    - adx.mail.smtp.hostname
    - adx.mail.smtp.port
    - adx.mail.smtp.username
    - adx.mail.smtp.password
    - adx.mail.smtp.to
    - adx.mail.subject
    - adx.mail.message
    - adx.security.userName
    - adx.security.password
  6. Any modification requiers NiFi restart!
 
### Launch:
 1. Navigate to /bin folder
 2. Execute cdm commands like the following: 
    - sudo ./nifi.sh start
    - sudo ./nifi.sh stop
    - sudo ./nifi.sh restart
 3. By config, the UI is located at http://localhost:8086/nifi
 4. Activate DistributedMapCacheClientService and configure DistributedMapCacheServer. For this:
    - Double-click on multi-import process group (PG)
    - Navigate to Configuration ->Controller Services.
    - Add the above mentioned services.
    - Specify Server Hostname in DistributedMapCacheClientService, like: localhost.
    - Enable both.
 5. In order to launch the pipeline, righ-click on the multi-import PG and select start!
 
 
### Features of multi-import pipeline:
 
   1. Able to import a single document as well as miltiple documents in one go, specified in input.txt
   2. Successfully processed documents are moved into a subfolder of success folder, like 2021-04-01-16-12-38-986-success-106537
   3. Failed documents are moved into a subfolder of failures folder like 2021-04-01-16-12-38-986-failure-106537
   4. Failure folder also contains input.txt with failed rows.
   5. Success folder also contains input.txt with succeeded rows.
   6. Failure logs (package_failures.log) are located in the corresponding failure folder.
   7. Email alert notification is sent once per package (per input.txt).
   8. Validation at the level of the whole package and a single row of input.txt.


