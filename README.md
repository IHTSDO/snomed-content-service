snomed-content-service
======================

Graph based Snomed Content Service

This work was superseded by the Snowowl EMF-MySQL based terminology server.
As such this is offered as an interesting piece of code and is not a supported project by IHTSDO

This can be installed using out ansible role found at: 

https://github.com/IHTSDO/ihtsdo-ansible/tree/master/roles/IHTSDO.refsetService

Which is usually run via:

https://github.com/IHTSDO/ihtsdo-ansible/blob/master/otf_refsetService.yml

Following are command and program for various data loading needs. In order to make it repeatable I will need make some tweaks so that we do not duplicate data. Also db connection properties file I can publish from container project as they being used by multiple projects.

To create SNOMED Graph Schema & Index

java -jar snomed-graph-indexer-0.1.1-SNAPSHOT.jar ../src/main/resources/snomed-graph-es-dev.properties schema
java -jar snomed-graph-indexer-0.1.1-SNAPSHOT.jar ../src/main/resources/snomed-graph-es-dev.properties index

To load SNOMED Terminology Data 

java -jar snomed-graph-loader-0.1.1-SNAPSHOT.jar -config ../../snomed-graph-indexer/src/main/resources/snomed-graph-es-dev.properties -cf sct2_Concept_Snapshot_INT_20140731.txt -type snapshot  -bSize 1000  -reload true > concept.log

java -jar snomed-graph-loader-0.1.1-SNAPSHOT.jar -config ../../snomed-graph-indexer/src/main/resources/snomed-graph-es-dev.properties -df sct2_Description_Snapshot_INT_20140731.txt -type audit  -bSize 1000  -reload true > description.log

java -jar snomed-graph-loader-0.1.1-SNAPSHOT.jar -config ../../snomed-graph-indexer/src/main/resources/snomed-graph-es-dev.properties -df sct2_Description_Snapshot_INT_20140731.txt -type audit -subType synonym -bSize 1000 -reload true > synonym.log

java -jar snomed-graph-loader-0.1.1-SNAPSHOT.jar -config ../../snomed-graph-indexer/src/main/resources/snomed-graph-es-dev.properties -df sct2_Description_Snapshot_INT_20140731.txt -type audit -subType definition -bSize 1000  -reload true > def.log

java -jar snomed-graph-loader-0.1.1-SNAPSHOT.jar -config ../../snomed-graph-indexer/src/main/resources/snomed-graph-es-dev.properties -rf sct2_Relationship_Snapshot_INT_20140731.txt -type audit  -bSize 1000  -reload true > rel.log

To create Refset Graph Schema & Index

java -jar refset-graph-indexer-0.1.1-SNAPSHOT.jar ../src/main/resources/refset-graph-es-dev.properties schema
java -jar refset-graph-indexer-0.1.1-SNAPSHOT.jar ../src/main/resources/refset-graph-es-dev.properties index
