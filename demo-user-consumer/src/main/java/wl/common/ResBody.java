package wl.common;

import java.io.Serializable;

public class ResBody<T extends Object> implements Serializable {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = -3468560134846477959L;
        private String retCode;
        private String retDesc;
        private T rspBody;

        /**
         * 获取 retCode
         * @return 返回 retCode
         */
        public String getRetCode() {
            return retCode;
        }
    /**
     * 设置 retCode
     * @param retCode
     */
    public void setRetCode(String retCode) {
            this.retCode = retCode;
        }

        /**
         * 获取 retDesc
         * @return 返回 retDesc
         */
        public String getRetDesc() {
            return retDesc;
        }

        /**
         * 设置 retDesc
         * @param retDesc
         */
        public void setRetDesc(String retDesc) {
            this.retDesc = retDesc;
        }

        /**
         * 获取 rspBody
         * @return 返回 rspBody
         */
        public T getRspBody() {
            return rspBody;
        }

        /**
         * 设置 rspBody
         * @param rspBody
         */
        public void setRspBody(T rspBody) {
            this.rspBody = rspBody;
        }

        /**
         * 重载方法
         * @return
         */
        @Override
        public String toString() {
            return "ResBody [retCode=" + retCode + ", retDesc=" + retDesc
                    + ", rspBody=" + rspBody + "]";
        }
}
