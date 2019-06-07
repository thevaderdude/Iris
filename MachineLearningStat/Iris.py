iimport numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from scipy import stats
from scipy.interpolate import *
from matplotlib import pylab


x = np.zeros((150, 5))
y = np.zeros((150, 3))
p = np.random.random((3, 5))
x_train = np.zeros((105, 5))
y_train = np.zeros((105, 3))
x_test = np.zeros((45, 5))
y_test = np.zeros((45, 3))
ls = 0
f = False


def load_data(lr, fart):
    global ls, x, y, f
    f = fart
    ls = lr
    df = pd.read_csv('iris.csv')
    df = df.sample(frac=1)
    # print(df)
    if fart:
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
    else:
        it = np.nditer(x, op_flags=['readwrite'], flags=['multi_index'])
        for x2 in it:
            x1 = it.multi_index[0]
            y1 = it.multi_index[1]
            ind = df.iloc[x1, y1]

            if ind == 'Versicolor':
                y[x1, 1] = 1
            elif ind == 'Virginica':
                y[x1, 2] = 1
            elif ind == 'Setosa':
                y[x1, 0] = 1

            if y1 == 0:
                x[x1, y1] = 1
            else:
                x[x1, y1] = df.iloc[x1, y1 - 1]


def sigmoid(x1):
    return 1 / (1 + np.power(np.e, -x1))


def h(num):
    return sigmoid((np.dot(p, x[num])))


def cost():
    if f:
        h1 = np.zeros((105, 3))
        it = np.nditer(h1, op_flags=['readwrite'], flags=['multi_index'])
        for h2 in it:
            h1[it.multi_index[0]] = h(it.multi_index[0])
        return (.5 * np.sum(np.power((h1 - y), 2))) / 105
    else:
        h1 = np.zeros((150, 3))
        it = np.nditer(h1, op_flags=['readwrite'], flags=['multi_index'])
        for h2 in it:
            h1[it.multi_index[0]] = h(it.multi_index[0])
        return (.5 * np.sum(np.power((h1 - y), 2))) / 150


def learn(epoch):
    global x, y, f
    if f:
        x = x_train
        y = y_train
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
    else:
        for i in range(0, epoch):
            p_temp = np.zeros((3, 5))
            h1 = np.zeros((150, 3))
            it = np.nditer(h1, op_flags=['readwrite'], flags=['multi_index'])
            for h2 in it:
                h1[it.multi_index[0]] = h(it.multi_index[0])
            p_temp = ls * (np.dot(np.transpose(h1 - y), x)) / 150

            p -= p_temp
            if i % 100 == 0:
                print(str(i) + '  ' + str(test()))


def test():
    if f:
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
        return 100 * num_right / 45
    else:
        num_right = 0

        h1 = np.zeros((150, 3))
        it = np.nditer(h1, op_flags=['readwrite'], flags=['multi_index'])
        for h2 in it:
            h1[it.multi_index[0]] = h(it.multi_index[0])
        for i in range(0, 150):
            # print(str(max_ind(h1[i])) + '  ' + str(max_ind(y[i])))
            if max_ind(h1[i]) == max_ind(y[i]):
                num_right += 1
        return 100 * num_right / 150


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
    n = 50
    x_plot = np.zeros(n)
    y_plot = np.zeros(n)
    load_data(.003, False)
    for i in range(0, n):
        x_plot[i] = x_plot[i-1] + 130 + (10 * i)
        learn(130 + (10 * i))
        # y_plot[i] = cost()
        y_plot[i] = test()
        # reset_vars()
    l_x_plot = np.log(x_plot)
    l_y_plot = (y_plot)
    p1 = np.polyfit(l_x_plot, l_y_plot, 1)
    slope, intercept, r_value, p_value, std_error = stats.linregress(l_x_plot, l_y_plot)
    mn = np.min(l_x_plot)
    mx = np.max(l_x_plot)
    x1 = np.linspace(mn, mx, n)
    line = slope * l_x_plot + intercept
    plt.figure(1)
    plt.plot(l_x_plot, l_y_plot, 'ob')
    plt.plot(x1, np.polyval(p1, x1), '-r')
    pylab.title("Effect of Learning Cycles on Performance of Iris  Algorithm")
    print(r_value)
    print(p_value)
    res = l_y_plot - np.polyval(p1, l_x_plot)
    plt.figure(0)
    plt.plot(l_x_plot, res, 'or')
    line2 = np.zeros(50)
    plt.plot(x1, line2, '-r')
    plt.plot()
    plt.show()


def get_dat_learning():
    n = 50
    x_plot = np.zeros(n)
    y_plot = np.zeros(n)
    for i in range(0, n):
        l = .001 + .001 * i
        x_plot[i] = l
        load_data(l, False)
        learn(1000)
        # y_plot[i] = cost()
        y_plot[i] = test()
        reset_vars()
    l_x_plot = np.log(x_plot)
    # l_x_plot = np.log(l_x_plot)
    l_y_plot = (y_plot)
    p1 = np.polyfit(l_x_plot, l_y_plot, 1)
    slope, intercept, r_value, p_value, std_error = stats.linregress(l_x_plot, l_y_plot)
    mn = np.min(l_x_plot)
    mx = np.max(l_x_plot)
    x1 = np.linspace(mn, mx, n)
    line = slope * l_x_plot + intercept
    plt.figure(1)
    plt.plot(l_x_plot, l_y_plot, 'ob')
    plt.plot(x1, np.polyval(p1, x1), '-r')
    pylab.title("Effect of Learning Speed on Performance of Iris Algorithm")
    print(r_value)
    print(p_value)
    res = l_y_plot - np.polyval(p1, l_x_plot)
    plt.figure(0)
    plt.plot(l_x_plot, res, 'or')
    line2 = np.zeros(50)
    plt.plot(x1, line2, '-r')
    plt.plot()
    plt.show()


def plot_test():
    x_plot = np.arange(0, 50)
    y_plot = np.arange(0, 50)
    slope, intercept, r_value, p_value, std_error = stats.linregress(x_plot, y_plot)
    mn = np.min(x_plot)
    mx = np.max(x_plot)
    x1 = np.linspace(mn, mx, 50)
    line = slope * x_plot + intercept
    plt.figure(1)
    plt.plot(x_plot, y_plot, 'ob')
    plt.plot(x1, line, '-r')
    pylab.title("Effect of Learning Cycles on Performance of Iris  Algorithm")
    res = line - y_plot
    plt.figure(0)
    plt.plot(x_plot, res, 'or')
    plt.show()


get_dat_learning()
# load_data(.003)
# learn(500)
# print(test())
# reset_vars()
# load_data(.003)
# learn(500)
# print(test())
# plot_test()

