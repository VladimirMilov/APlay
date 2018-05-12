echo "######################################################"
echo "# PACKAGING"
echo "######################################################"
mvn clean package -Dlocal

cd events-client; npm i; cd ..;

echo "######################################################"
echo "# BUILDING APPS"
echo "######################################################"
docker-compose -f development.yml build
