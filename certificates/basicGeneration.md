openssl req -x509 -newkey rsa:4096 -keyout key.pem -out public_key.pem -sha256 -days 3650 -nodes -subj /C=XX/ST=Belgium/L=Liege/O=Amazy/OU=Auth/CN=Sauron


//openssl req -x509 -newkey rsa:4096 -keyout private_key.pem -out public_key.pem -sha256 -days 3650 -nodes -subj /C=XX/ST=Belgium/L=Liege/O=Amazy/OU=Auth/CN=Sauron



openssl pkcs8 -topk8 -inform PEM -outform PEM -in key.pem -out private_key.pem -nocrypt
