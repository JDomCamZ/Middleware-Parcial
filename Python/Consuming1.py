import socket
import sys
import time
import random

ip='192.168.0.18'
port=4444

mi_socket=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
mi_socket.connect((ip,port))


def working():
    timetosleep = random.random() * 3 + 1
    time.sleep(timetosleep)
    mensaje='listo\n'
    mi_socket.send(mensaje.encode())
    print("terminó trabajo")


mensaje='listo\n'
mi_socket.send(mensaje.encode())
respuesta=mi_socket.recv(4000).decode()

if(respuesta=="Is Producer or Consuming?\r\n"):
        mensaje='Consumer\n'
        mi_socket.send(mensaje.encode())
print("Esperando mensaje de Producing")

while True:
    respuesta=mi_socket.recv(4000).decode()    
    print("Poducing Mesage")
    print(respuesta)
    working()