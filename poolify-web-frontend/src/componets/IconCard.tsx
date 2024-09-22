import React from "react";
import {ProCard} from "@ant-design/pro-components";
import {Image} from "antd";


const IconCard: React.FC<{
    iconSrc: string,
    title: string,
    quantity: number
}> = ({iconSrc,title,quantity}) => {


    return (
        <ProCard split="vertical">
            <ProCard colSpan="30%">
                <Image src={iconSrc}></Image>
            </ProCard>
            <ProCard title={title} headerBordered>
                <div>{quantity}</div>
            </ProCard>
        </ProCard>
    );
};

export default IconCard
