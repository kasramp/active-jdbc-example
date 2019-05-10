# Spring Boot ActiveJDBC example

This example demonstrates how to wire up ActiveJDBC with Spring Boot in replacement of Hibernate.

You can find the tutorial about this example at the below link:
https://blog.madadipouya.com


This example provides three controllers as follows:
- User controller: supports CRUD operations for User resource.
- Song controller: supports CRUD operations for Song resource.
- Playlist controller: supports CRUD operations for Playlist resource.


## How to run

+ First start the `docker-compose.yml` file by running:

```bash
$ docker-compose -f docker-compose.yml up -d 
```

+ To run create database and tables run `dbupdate` profile as below:

```bash
$ ./mvnw clean compile -P dbcreate
```

That executes `db-migrator:create` goal.

+ And finally to run the application run:

```bash
$ ./mvn clean compile -P developer
```

The developer profile also takes care of generating the instruments as well as updating the db schema by running `db-migrator:migrate` goal. So you don't need to worry about it.

Once everything is up and running open the browser and go to [http://localhost:8080](http://localhost:8080). You should see Swagger to interact with.

If you wish to change any database settings just edit `database.properties` file.