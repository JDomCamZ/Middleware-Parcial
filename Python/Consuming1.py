import socket
import sys
ip='192.168.0.18'
port=4444

mi_socket=socket.socket()
mi_socket.connect((ip,port))

print("Esperando mensaje de Producing")
while True:
    respuesta=mi_socket.recv(4000).decode()
    print("Poducing Mesage")
    print(respuesta)