## Migrate DB
sbt flywayMigrate
## Clean DB 
sbt flywayClean
## Migrate DB Test
sbt test:flywayMigrate
## Clean DB Test
sbt test:flywayClean
##

