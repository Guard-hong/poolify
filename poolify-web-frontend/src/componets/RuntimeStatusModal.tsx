import React, {useEffect, useState} from "react";
import {ModalForm} from "@ant-design/pro-components";
import {ThreadPoolRuntimeStatusLineVO} from "@/api/pool";
import LineChart from "@ant-design/plots/es/components/line";
import dayjs from "dayjs";


const RuntimeStatusModal: React.FC<{
    data: ThreadPoolRuntimeStatusLineVO[]
    open: boolean
    setOpen: (open:boolean) => void
    onConfirm: () => Promise<boolean>
}> = ({data,open,setOpen,onConfirm}) => {
    const [lineConfig, setLineConfig] = useState<any>({
        xField: "date", // X-axis key
        yField: "activeThreadCount", // Y-axis key
        point: {
            size: 5, // Size of the points
            shape: "diamond", // Shape of the points
        },
        smooth: true, // Smooth the lines
        label: {
            style: {
                fill: "#aaa",
            },
        },
    });

    useEffect(()=>{
        const disposeData = data.map(item => {
            return {
                date: dayjs(item.date).format('hh:mm:ss'),
                // date: dayjs(item.date).format('YYYY-MM-DD'),
                activeThreadCount: item.activeThreadCount
            }
        })
        setLineConfig({
            ...lineConfig,
            data:disposeData
        })
        console.log(disposeData)
    },[data])


    return (
        <ModalForm<any>
            width="80vh"
            title="线程池参数可视化"
            open = {open}
            submitTimeout={2000}
            onFinish={onConfirm}
            onOpenChange={setOpen}
        >

            <LineChart {...lineConfig}></LineChart>

        </ModalForm>
    );
};

export default RuntimeStatusModal
