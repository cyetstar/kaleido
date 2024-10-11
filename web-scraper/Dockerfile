FROM python:3.7-slim-stretch

WORKDIR /app

COPY . .

EXPOSE 8001

ENV TZ=Asia/Shanghai

ENV PATH=$PATH:/opt/node-v10.16.0-linux-x64/bin

RUN apt update && \
    apt install -y wget && \
    wget https://nodejs.org/dist/v10.16.0/node-v10.16.0-linux-x64.tar.gz && \
    tar -xzf node-v10.16.0-linux-x64.tar.gz -C /opt/ && \
    rm -f node-v10.16.0-linux-x64.tar.gz && \
    pip3 install -r requirements.txt && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT ["gunicorn","-b","0.0.0.0:8001","application:app"]