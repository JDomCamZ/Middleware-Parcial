import socket
import sys
import time

ip='192.168.0.10'
port=4444

mi_socket=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
mi_socket.connect((ip,port))


def working():
    time.sleep(3)
    mensaje='listo\n'
    mi_socket.send(mensaje.encode())
    print("terminó trabajo")


mensaje='listo\n'
mi_socket.send(mensaje.encode())
print("Esperando mensaje de Producing")
while True:
    respuesta=mi_socket.recv(4000).decode()
    #print([respuesta])
    if(respuesta=="ATLAS\r\n"):
        print("DDDDDDD")
    working()
    print("Poducing Mesage")
    print(respuesta)