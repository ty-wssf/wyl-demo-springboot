# 使用Prometheus+Grafana搭建服务器监控系统

## Grafana安装及应用详解

**官网地址：https://grafana.com/**

**官方文档：https://grafana.com/grafana**

### Grafana介绍

Grafana是一个跨平台的开源的度量分析和可视化工具，可以通过将采集的数据查询然后可视化的展示，并及时通知。它主要有以下六大特点：

1. 展示方式：快速灵活的客户端图表，面板插件有许多不同方式的可视化指标和日志，官方库中具有丰富的仪表盘插件，比如热图、折线图、图表等多种展示方式；

2. 数据源：Graphite，InfluxDB，OpenTSDB，Prometheus，Elasticsearch，CloudWatch和KairosDB等；

3. 通知提醒：以可视方式定义最重要指标的警报规则，Grafana将不断计算并发送通知，在数据达到阈值时通过Slack、PagerDuty等获得通知；

4. 混合展示：在同一图表中混合使用不同的数据源，可以基于每个查询指定数据源，甚至自定义数据源；

5. 注释：使用来自不同数据源的丰富事件注释图表，将鼠标悬停在事件上会显示完整的事件元数据和标记；

6. 过滤器：Ad-hoc过滤器允许动态创建新的键/值过滤器，这些过滤器会自动应用于使用该数据源的所有查询。

### 下载安装

**1、下载**

官网下载地址：https://grafana.com/grafana/download

安装指南：http://docs.grafana.org/installation/rpm/

根据自己的系统版本和配置，下载对应的包，官方提供了如下说明，可直接按照说明进行下载：

![835d8153d9ca23f8f1a02f2c7cbb3f0a.png](https://img-blog.csdnimg.cn/img_convert/835d8153d9ca23f8f1a02f2c7cbb3f0a.png)

**linux直接下载**

```crystal
wget https://dl.grafana.com/oss/release/grafana-7.3.1-1.x86_64.rpm
```

**2、安装**

```undefined
yum install grafana-7.3.1-1.x86_64.rpm
```

**3、启动**

```vbscript
systemctl start grafana-server
```

**4、验证**

浏览器输入ip+port(默认端口3000)验证即可。默认用户名和密码为admin、admin

![d23cdfea4d2fcfb16a8eceb572d1b4c6.png](https://img-blog.csdnimg.cn/img_convert/d23cdfea4d2fcfb16a8eceb572d1b4c6.png)

## Prometheus安装及应用详解

### 介绍

**1.什么是Prometheus？**

普罗米修斯是一个开源的系统监控及报警工具，在2016年加入了 Cloud Native Computing Foundation，是继Kubernetes之后的第二个托管项目。

**2.Prometheus的特征有什么？**

-  具有由metric名称和键值对标示的时间序列数据的多位数据模型
-  有一个灵活的查询语言promQL
-  不依赖分布式存储，只和本地磁盘有关
-  通过HTTP来拉取(pull)时间序列数据
-  也支持推送(push)方式添加时间序列数据
-  多种图形和仪表盘支持

**3.Prometheus的组件都有哪些？来张官方图：**

 ![img](https://img2020.cnblogs.com/blog/1715041/202011/1715041-20201129181943262-234489244.png)

-  Prometheus Server 用于定时抓取数据指标(metrics)、存储时间序列数据(TSDB)
-  Jobs/exporte 收集被监控端数据并暴露指标给Prometheus
-  Pushgateway 监控端的数据会用push的方式主动传给此组件，随后被Prometheus 服务定时pull此组件数据即可
-  Alertmanager 报警组件，可以通过邮箱、微信等方式
-  Web UI 用于多样的UI展示，一般为Grafana
-  还有一些例如配置自动发现目标的小组件和后端存储组件

**4.什么时候使用Prometheus**

-  监控的对象动态可变，无法预先配置的时候
-  Prometheus 是专为云环境(k8s/docker)提供的监控工具
-  想要更直观更简单的直接观察某项指标的数据变化时

**5.看到一个写的非常不错的关Prometheus存储的文章**

https://www.cnblogs.com/zqj-blog/p/12205063.html

### 搭建

**1.安装Prometheus**

官网下载地址：https://prometheus.io/download/  选择自己所需版本即可

```shell
## 解压安装
tar zxf prometheus-2.22.0.linux-amd64.tar.gz -C /opt/vfan/
mv prometheus-2.22.0.linux-amd64 prometheus-2.22.0
cd prometheus-2.22.0/
 
## 可以通过--help或--version查看服务启动参数和版本等
./prometheus --help
./prometheus --version
 
## 启动服务，并指定配置文件
## 解压安装
tar zxf prometheus-2.22.0.linux-amd64.tar.gz -C /opt/vfan/
mv prometheus-2.22.0.linux-amd64 prometheus-2.22.0
cd prometheus-2.22.0/
 
## 可以通过--help或--version查看服务启动参数和版本等
./prometheus --help
./prometheus --version
 
## 启动服务，并指定配置文件
nohup ./prometheus --config.file="prometheus.yml" &> /dev/null &
 
## 查看端口占用情况(默认9090)
[root@VM-0-10-centos prometheus-2.22.0]# ss -tnlp
```

或者直接使用docker容器运行，直接挂载一下配置文件：

```shell
docker run \
    -p 9090:9090 \
    -v /path/to/prometheus.yml:/etc/prometheus/prometheus.yml \
    prom/prometheus
```

查看默认prometheus.yml文件：vim prometheus.yml

```yml
# my global config
global:
  scrape_interval:     15s # Set the scrape interval to every 15 seconds. Default is every 1 minute.
  evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is every 1 minute.
  # scrape_timeout is set to the global default (10s).

# Alertmanager configuration
alerting:
  alertmanagers:
  - static_configs:
    - targets:
      # - alertmanager:9093

# Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
rule_files:
  # - "first_rules.yml"
  # - "second_rules.yml"

# A scrape configuration containing exactly one endpoint to scrape:
# Here it's Prometheus itself.
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: 'prometheus'

    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.

    static_configs:
    - targets: ['localhost:9090']
```

> 目前只在监控Prometheus本机

可以登录普罗米修斯(服务器ip:9090)web界面，Status—>Rules下查看目前正在监控的目标

![img](https://img2020.cnblogs.com/blog/1715041/202011/1715041-20201129183158778-286333475.png)

 

可以看到获取监控信息的终点是 本机ip+端口+/metrics：

 ![img](https://img2020.cnblogs.com/blog/1715041/202011/1715041-20201129183240272-1942594323.png)



也可以查看监控图形：Graph—>选择监控项—>Execute

 ![img](https://img2020.cnblogs.com/blog/1715041/202011/1715041-20201129183252858-1923586689.png)

> 这种图形界面显然不太直观，所以引入Grafana。

 

**2.安装node-exporter插件，添加监控机器**

下载链接：https://prometheus.io/download/  选择自己所需版本即可

```shell
## 解压安装
tar zxf node_exporter-1.0.1.linux-amd64.tar.gz -C /opt/vfan/
mv node_exporter-1.0.1.linux-amd64 node_exporter
cd node_exporter/
 
## 可以查看服务启动参数
./node_exporter --help
    --web.listen-address=":9100"    #可以指定监听端口
    --collector.ntp.server="127.0.0.1"  #可以指定ntp server
 
## 直接执行即可，--web.listen-address参数可以指定监听端口，默认9100。
nohup ./node_exporter --web.listen-address=":9100" &> /dev/null &
 
[root@VM-0-10-centos node_exporter]# ss -tnlp
```



prometheus.yaml中添加node_exporter配置

```yaml
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: 'prometheus'

    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.

    static_configs:
    - targets: ['localhost:9090']
  
  - job_name: 'node_demo1'
    static_configs:
    - targets: ['localhost:9100']
```

 

然后重启普罗米修斯服务，重启后再次查看监控目标：

 ![img](https://img2020.cnblogs.com/blog/1715041/202011/1715041-20201129183519750-912819019.png)

> 已经开始监控新的node



## 参考资料

[新一代监控神器Prometheus+Grafana介绍及使用](https://www.cnblogs.com/v-fan/p/14057366.html)

