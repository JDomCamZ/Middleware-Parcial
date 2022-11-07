import socket
import sys
import threading
ip='192.168.0.18'
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


threadReciv=threading.Thread(name="h1",target=recivir,args=())    
threadReciv.start()

while True:
    mensaje=input()
    mensaje+='\n'
    mi_socket.send(mensaje.encode())
    if mensaje=="adios\n":
        mi_socket.close()        
        sys.exit()


