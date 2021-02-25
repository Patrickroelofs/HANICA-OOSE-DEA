# Build
mvn clean package && docker build -t org.example/Workshop REST .

# RUN

docker rm -f Workshop REST || true && docker run -d -p 8080:8080 -p 4848:4848 --name Workshop REST org.example/Workshop REST 