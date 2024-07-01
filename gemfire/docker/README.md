# pull the container image

```
docker pull apachegeode/geode
```

This may take a while depending on your internet connection, but it's worth since this is a one time step and you endup with a container that is tested and ready to be used for development. It will download Gradle and as part of the build, project dependencies as well.

# Starting a locator and gfsh

1. Then you can start gfsh as well in order to perform more commands:

```
docker run -it -p 10334:10334 -p 7575:7575 -p 40404:40404 -p 1099:1099  apachegeode/geode gfsh
```


From this point you can pretty much follow [Apache Geode in 5 minutes](https://cwiki.apache.org/confluence/display/GEODE/Index#Index-Geodein5minutes) for example:

```
gfsh> start locator --name=locator1 --port=10334
gfsh> start server --name=server1 --server-port=40404
```

query cluster status

```
list members
```
Create a region:
```
gfsh> create region --name=hello --type=REPLICATE 
gfsh> create region --name=People --type=REPLICATE
```
query region
```
gfsh>query --query="select * from /hello"
```

clean region
```
remove --all --region=People
```
Finally, shutdown the Geode server and locator:
```
gfsh> shutdown --include-locators=true
```

https://geode.apache.org/docs/guide/114/getting_started/15_minute_quickstart_gfsh.html