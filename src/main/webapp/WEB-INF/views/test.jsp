         <![CDATA[
        		SELECT * FROM (
					    SELECT
					        i.*, ROWNUM AS rn
					    FROM (
					            SELECT
								    i.itemId,
								    i.itemName,
								    i.manufacturer,
								    i.itemStock,
								    s.stockOrderId,
								    s.stockOrderQty,
								    s.receivedQty,
								    s.orderDate,
								    s.receivedDate,
								    s.isReceived
								FROM
								    tbl_item i
								LEFT JOIN
								    tbl_stockInfo s ON i.itemId = s.itemId AND s.isReceived = 'N'
								Order by 
		]]> 

         <choose>
	        <when test="sortColumn != null and sortColumn == 'itemStock'">
	            <![CDATA[ i.itemStock ]]>
	        </when>
	        <when test="sortColumn != null and sortColumn == 'stockOrderQty'">
	            <![CDATA[ s.stockOrderQty ]]>
	        </when>
	        <otherwise>
	        	<![CDATA[ i.itemId ]]>
	        </otherwise>
	     </choose>
        <![CDATA[ desc) i
						WHERE
        ]]>