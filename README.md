<div>
    <h3>阻塞IO、非阻塞IO、异步阻塞IO、异步非阻塞IO</h3>
        <p>背景：聊天室，A与B聊天 
        <br/>
        <i>注：</i><br/>
        <i>- getChannelMessage()是伪代码，这里用作获取通道内的信息</i><br/>
        <i>- getChannelStatus()是伪代码，这里用作获取通道状态</i>
    </p>
    <ul>
        <li>阻塞IO : A调用getChannelMessage()获得B发送给自己的消息，若没有则线程停在那里，无法做其他操作。</li>
        <li>非阻塞IO : A调用getChannelStatus()立刻返回状态，根据状态可以做对应操作，但需要不停的<b>主动</b>通过getChannelStatus()来获得状态，来进行相关操作。</li>
        <li>异步阻塞IO : A调用getChannelStatus()立刻返回状态，根据状态可以做对应操作，但需要不停的<b>主动</b>通过getChannelStatus()来获得状态，来进行相关操作。</li>
    </ul>
</div>