import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from scipy import stats
from matplotlib import pylab
import plotly.plotly as py
import plotly.graph_objs as go

x = np.zeros((150, 5))
y = np.zeros((150, 3))
p = np.random.random((3, 5))
x_train = np.zeros((105, 5))
y_train = np.zeros((105, 3))
x_test = np.zeros((45, 5))
y_test = np.zeros((45, 3))
ls = 0


def load_data(lr):
    global ls, x, y
    ls = lr
    df = pd.read_csv('iris.csv')
    df = df.sample(frac=1)
    # print(df)
    it = np.nditer(x, op_flags=['readwrite'], flags=['multi_index'])
    for x2 in it:
        x1 = it.multi_index[0]
        y1 = it.multi_index[1]
        ind = df.iloc[x1, y1]

        if ind == 'Versicolor':
            if x1 < 105:
                y_train[x1, 1] = 1
            else:
                y_test[x1-105, 1] = 1
        elif ind == 'Virginica':
            if x1 < 105:
                y_train[x1, 2] = 1
            else:
                y_test[x1-105, 2] = 1
        elif ind == 'Setosa':
            if x1 < 105:
                y_train[x1, 0] = 1
            else:
                y_test[x1-105, 0] = 1

        if y1 == 0:
            if x1 < 105:
                x_train[x1, y1] = 1
            else:
                x_test[x1-105, y1] = 1
        else:
            if x1 < 105:
                x_train[x1, y1] = df.iloc[x1, y1 - 1]
            else:
                x_test[x1-105, y1] = df.iloc[x1, y1 - 1]
    x = x_train
    y = y_train

def sigmoid(x1):
    return 1 / (1 + np.power(np.e, -x1))


def h(num):
    return sigmoid((np.dot(p, x[num])))


def cost():
    h1 = np.zeros((105, 3))
    it = np.nditer(h1, op_flags=['readwrite'], flags=['multi_index'])
    for h2 in it:
        h1[it.multi_index[0]] = h(it.multi_index[0])
    return (.5 * np.sum(np.power((h1 - y), 2))) / 105


def learn(epoch):
    for i in range(0, epoch):
        p_temp = np.zeros((3, 5))
        h1 = np.zeros((105, 3))
        it = np.nditer(h1, op_flags=['readwrite'], flags=['multi_index'])
        for h2 in it:
            h1[it.multi_index[0]] = h(it.multi_index[0])
        p_temp = ls * (np.dot(np.transpose(h1 - y), x)) / 105
        global p
        p -= p_temp
        if i % 1000 == 0:
            print(str(i) + '  ' + str(cost()))


def test():
    num_right = 0
    global x, y
    x = x_test
    y = y_test
    h1 = np.zeros((45, 3))
    it = np.nditer(h1, op_flags=['readwrite'], flags=['multi_index'])
    for h2 in it:
        h1[it.multi_index[0]] = h(it.multi_index[0])
    for i in range(0, 45):
        # print(str(max_ind(h1[i])) + '  ' + str(max_ind(y[i])))
        if max_ind(h1[i]) == max_ind(y[i]):
            num_right += 1
    return num_right / 45


def max_ind(par):
    m = 0
    for i in range(0, 3):
        if par[m] < par[i]:
            m = i
    return m


def reset_vars():
    global p, x, y, x_train, y_train, x_test, y_test, ls
    x = np.zeros((150, 5))
    y = np.zeros((150, 3))
    p = np.random.random((3, 5))
    x_train = np.zeros((105, 5))
    y_train = np.zeros((105, 3))
    x_test = np.zeros((45, 5))
    y_test = np.zeros((45, 3))
    ls = 0


def get_dat_trials():
    x_plot = np.zeros(50)
    y_plot = np.zeros(50)
    for i in range(0, 50):
        load_data(.003)
        x_plot[i] = 5000 * i
        learn(5000 * i)
        y_plot[i] = test()
        reset_vars()

    slope, intercept, r_value, p_value, std_error = stats.linregress(x_plot, y_plot)
    mn = np.min(x_plot)
    mx = np.max(x_plot)
    x1 = np.linspace(mn, mx, 50)
    line = slope * x_plot + intercept
    plt.plot(x_plot, y_plot, 'ob')
    plt.plot(x1, line, '-r')
    pylab.title("Effect of Learning Cycles on Performance of Iris  Algorithm")
    plt.show()


def plot_test():
    x_plot = np.arange(0, 50)
    y_plot = np.arange(0, 50)
    slope, intercept, r_value, p_value, std_error = stats.linregress(x_plot, y_plot)
    mn = np.min(x_plot)
    mx = np.max(x_plot)
    x1 = np.linspace(mn, mx, 50)
    line = slope * x_plot + intercept
    plt.plot(x_plot, y_plot, 'ob')
    plt.plot(x1, line, '-r')
    pylab.title("Effect of Learning Cycles on Performance of Iris  Algorithm")
    plt.show()


get_dat_trials()
# load_data(.003)
# learn(500)
# print(test())
# reset_vars()
# load_data(.003)
# learn(500)
# print(test())

