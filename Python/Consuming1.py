import socket
import sys
import time

ip='192.168.0.18'
port=4444

mi_socket=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
mi_socket.connect((ip,port))


def working():
    time.sleep(3)
    mensaje='listo\n'
    mi_socket.send(mensaje.encode())
    print("terminó trabajo")


print("Esperando mensaje de Producing")
while True:
    respuesta=mi_socket.recv(4000).decode()
    print([respuesta])
    working()
    if(respuesta=="ATLAS\r\n"):
        
        print("DDDDDDD")
    if(respuesta==2):
        print("BBBBBBBB")
    print("Poducing Mesage")
    print(respuesta)