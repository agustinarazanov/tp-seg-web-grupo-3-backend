docker run --rm -d --name seg-web-tp-db \
-e MYSQL_ROOT_PASSWORD=my-secret-pw -p 3306:3306 \
-v ~/sg-web-mysql-db:/var/lib/mysql \
mysql