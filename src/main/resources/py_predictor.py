#!C:\Users\Siddesh\AppData\Local\Programs\Python\Python38-32\python.exe
import numpy as np
import pandas as pd
from sklearn.linear_model import LinearRegression
# from sklearn.model_selection import cross_val_score,cross_validate,train_test_split
# from sklearn.preprocessing import StandardScaler,Normalizer, RobustScaler
# from sklearn.metrics import *
# import seaborn as sns
# from sklearn.discriminant_analysis import LinearDiscriminantAnalysis as LDA
import sys
import pickle
import os

#a = ""
#a = a + sys.argv[1] + " "
#a = a + sys.argv[2] + " "
#a = a + sys.argv[3] + " "
#a = a + sys.argv[4]

a = sys.stdin.read()
if a == "" or a == " ":
    sys.exit()
data = a.split()
for each in range(0,len(data)):
    data[each] = float(data[each])
data = np.array(data).reshape(1,-1)
model_path = "C:\\Users\\Siddesh\\IdeaProjects\\streamingdata\\src\\main\\resources\\output.pkl"
with open(model_path,'rb') as f:
    model = pickle.load(f)
pred = model.predict(data)
try:
   sys.stdout.write(str(pred[0]))
except Exception as e:
       print("Cannot return the path beacause ",e)

