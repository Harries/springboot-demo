## windows set env

$env:KUDU_QUICKSTART_VERSION = "1.12.0"
$env:KUDU_QUICKSTART_IP= "10.11.68.77"
Get-ChildItem Env:

## linux set env
export KUDU_QUICKSTART_VERSION="1.12.0"
export KUDU_QUICKSTART_IP=$(ifconfig | grep "inet " | grep -Fv 127.0.0.1 | awk '{print $2}' | tail -1)

## run

docker-compose -f docker/quickstart.yml up -d

## stop

docker-compose -f docker/quickstart.yml down
