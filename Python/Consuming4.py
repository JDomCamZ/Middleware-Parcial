import socket
import sys
import threading
import time
ip='192.168.0.10'
port=4444

mi_socket=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
mi_socket.connect((ip,port))
mensaje=""

def recivir():
    while(True):
        if(mensaje!="adios\n"):
            break
        respuesta=mi_socket.recv(4000).decode()
        print(respuesta)
        working()


def working():
    time.sleep(3)
    mensaje='listo\n'
    mi_socket.send(mensaje.encode())
    print("terminó trabajo")

threadReciv=threading.Thread(name="h1",target=recivir,args=())    
threadReciv.start()

while True:
    mensaje=input()
    mensaje+='\n'
    mi_socket.send(mensaje.encode())
    if mensaje=="adios\n":
        mi_socket.close()        
        sys.exit()
