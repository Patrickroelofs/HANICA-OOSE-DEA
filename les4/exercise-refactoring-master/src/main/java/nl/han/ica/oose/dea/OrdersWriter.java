package nl.han.ica.oose.dea;

public class OrdersWriter {
    private Orders orders;

    public OrdersWriter(Orders orders) {
        this.orders = orders;
    }

    public String getContents() {
        StringBuffer StringBuffer = new StringBuffer("{\"orders\": [");

        for (int i = 0; i < orders.getOrdersCount(); i++) {
            Order order = orders.getOrder(i);
            StringBuffer.append("{\"id\": ");
            StringBuffer.append(order.getOrderId());
            StringBuffer.append(", \"products\": [");
            for (int j = 0; j < order.getProductsCount(); j++) {
                Product product = order.getProduct(j);

                StringBuffer.append("{\"code\": \"");
                StringBuffer.append(product.getCode());
                StringBuffer.append("\", \"color\": \"");
                StringBuffer.append(product.getColorString());
                StringBuffer.append("\", ");

                if (product.getSize() != -1) {
                    StringBuffer.append("\"size\": \"");
                    StringBuffer.append(product.getSizeString());
                    StringBuffer.append("\", ");
                }

                StringBuffer.append("\"price\": ");
                StringBuffer.append(product.getPrice());
                StringBuffer.append(", \"currency\": \"");
                StringBuffer.append(product.getCurrency());
                StringBuffer.append("\"}, ");
            }

            if (order.getProductsCount() > 0) {
                StringBuffer.delete(StringBuffer.length() - 2, StringBuffer.length());
            }

            StringBuffer.append("]}, ");
        }

        if (orders.getOrdersCount() > 0) {
            StringBuffer.delete(StringBuffer.length() - 2, StringBuffer.length());
        }

        return StringBuffer.append("]}").toString();
    }
}
