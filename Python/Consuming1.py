import socket
import sys
ip='192.168.0.10'
port=4444

mi_socket=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
mi_socket.connect((ip,port))

print("Esperando mensaje de Producing")
while True:
    respuesta=mi_socket.recv(4000).decode()
    print([respuesta])
    if(respuesta=="ATLAS\r\n"):
        print("DDDDDDD")
    if(respuesta==2):
        print("BBBBBBBB")
    print("Poducing Mesage")
    print(respuesta)