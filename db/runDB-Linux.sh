docker run --rm -d --name seg-web-tp-db \
-e MYSQL_ROOT_PASSWORD=fuv938S763j -p 3307:3306 \
mysql
# -v ~/seg-web-mysql-db:/var/lib/mysql \