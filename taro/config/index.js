const config = {
    projectName: 'test02',
    date: '2021-10-19',
    designWidth: 750,
    deviceRatio: {
        640: 2.34 / 2,
        750: 1,
        828: 1.81 / 2
    },
    sourceRoot: 'src',
    outputRoot: 'dist',
    plugins: [],
    defineConstants: {},
    copy: {
        patterns: [],
        options: {}
    },
    framework: 'vue3',
    mini: {
        postcss: {
            pxtransform: {
                enable: true,
                config: {

                }
            },
            url: {
                enable: true,
                config: {
                    limit: 1024 // 设定转换尺寸上限
                }
            },
            cssModules: {
                enable: false, // 默认为 false，如需使用 css modules 功能，则设为 true
                config: {
                    namingPattern: 'module', // 转换模式，取值为 global/module
                    generateScopedName: '[name]__[local]___[hash:base64:5]'
                }
            }
        }
    },
    h5: {
        publicPath: '/',
        staticDirectory: 'static',
        devServer: { //记住，别写错了devServer//设置本地默认端口  选填
            port: 8080,
            proxy: { //设置代理，必须填
                '/api': { //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
                    target: 'http://localhost:9090', //代理的目标地址
                    changeOrigin: true, //是否设置同源，输入是的
                    pathRewrite: { //路径重写
                        '/api': '' //选择忽略拦截器里面的单词
                    }
                }
            }
        },
        postcss: {
            autoprefixer: {
                enable: true,
                config: {}
            },
            cssModules: {
                enable: false, // 默认为 false，如需使用 css modules 功能，则设为 true
                config: {
                    namingPattern: 'module', // 转换模式，取值为 global/module
                    generateScopedName: '[name]__[local]___[hash:base64:5]'
                }
            }
        },
        webpackChain(chain) {
            chain.resolve.alias
                .set(
                    '@tarojs/components$',
                    '@tarojs/components/dist-h5/vue3/index.js'
                )
        }
    }
}

module.exports = function(merge) {
    if (process.env.NODE_ENV === 'development') {
        return merge({}, config, require('./dev'))
    }
    return merge({}, config, require('./prod'))
}