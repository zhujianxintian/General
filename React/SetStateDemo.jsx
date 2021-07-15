import React from 'react'

class SetStateDemo extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            count: 0
        }
    }

    render() {
        return (
            <div>
                <h1>关于 setState 函数是异步还是同步的测试</h1>
                <p>setState 在可控情况下是异步的，在不可控情况下是同步的</p>
                <p>count: {this.state.count}</p>
                <button onClick={this.increment.bind(this)}>增加</button>
            </div>
        )
    }

    async increment() {
        // 当这个点击事件触发时，this.setState 是异步，数据还没有更新，下面的控制台输出语句就打印了 (0)，打印完成之后 this.setState 异步执行完成，数据和页面更新 (1)
        // 所以才会慢一步，所以不能直接将要操作更新后数据的语句写在下面
        // this.setState({count: this.state.count + 1});
        // console.log(this.state.count);

        // setState 有一个回调函数，当数据更新完成时，就会执行，所以可以把操作放在回调里面
        // this.setState({count: this.state.count + 1}, () => {
        //     console.log(this.state.count);
        // });

        // 当然也可能不使用回调函数的方式
        await this.setStateAsync({count: this.state.count + 1});
        console.log(this.state.count);
    }

    /**
     * state 是一个对象，对象中有你要改变的 state 数据，改变完成之后就调用 resolve 函数
     * @param {*} state 
     */
    setStateAsync(state) {
        return new Promise(resolve => {
            this.setState(state, resolve);
        });
    }
}

export default SetStateDemo