# app.py

import numpy as np
import cv2

img = cv2.imread('in/carpincho_matero.png', 1)
#cv2.imwrite('out/oscar_2_sinFacha.png',img)

print(len(img))
print(len(img[0]))
print(len(img[1]))
# height, width = img.shape
# print(height, width)

alto = len(img) - 1
ancho = len(img[0]) - 1 

escala = 4

X = -escala
Y = -escala
outStr = "grupo("

for i, row in enumerate(img):
    X = -escala
    Y += escala

    for j, px in enumerate(row):
        X += escala
        newRectangle = "color["+ str(px[2]) + ", "+str(px[1])+", "+str(px[0]) +"]"
        newRectangle += "(rectangulo["+str(X)+" @ "+str(Y)+", "+str(X + escala)+" @ "+str(Y + escala)+"])\n"
        if(i == alto and j == ancho ):
            outStr += newRectangle
        else:
            outStr += newRectangle + ","

outStr += ")"

f = open("out/carpincho_matero.txt", "w+")
f.write(outStr)
f.close()
#print (outStr)
