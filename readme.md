# fileupload

This project uses Dropzonejs, CXF, and Spring to upload multiple files. The stack is a Bootstrap enabled webpage sending to an Apache CXF enabled endpoint backed with Spring Transaction management to persist to a MySQL database.

The persistence is done by straight JDBC to minimize the amount of objects built to get the file to the database.