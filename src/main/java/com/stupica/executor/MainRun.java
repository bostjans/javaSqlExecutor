package com.stupica.executor;


import com.stupica.ConstGlobal;
import com.stupica.GlobalVar;

import com.stupica.jdbc.ConnectionHandler;
import com.stupica.jdbc.StatementHandler;
import com.stupica.mainRunner.MainRunBase;
import jargs.gnu.CmdLineParser;

import java.io.File;
import java.util.Date;
import java.util.logging.Logger;


/**
 * Created by bostjans on 07/09/16.
 */
public class MainRun extends MainRunBase {
    // Variables
    //
    boolean bIsModeTest = true;
    boolean bIsModeVerbose = true;

    int     iNumOfFieldMax = 99;

    long    iMaxNumOfRows = 0;

    String  sDelimiter = ",";
    String  sQuoteFieldChar = "\"";
    char    cQuoteFieldChar = '"';

    String  sInputSql = null;
    String  sJdbcConn = "jdbc:mysql://localhost/test";
    //String  sJdbcConn = "jdbc:mysql://localhost:3306/lenkodb";
    //String  sJdbcClass = "com.mysql.jdbc.Driver";

    String  sJdbcUser = "test";
    String  sJdbcPsw = "test";

    //String[]    arrField = null;
    //String[]    arrOper = null;

    /**
     * Main object instance variable;
     */
    private static MainRun objInstance;

    CmdLineParser.Option obj_op_source = null;
    CmdLineParser.Option obj_op_dest = null;
    CmdLineParser.Option obj_op_user = null;
    CmdLineParser.Option obj_op_psw = null;
    //CmdLineParser.Option obj_op_table = null;
    //CmdLineParser.Option obj_op_oper = obj_parser.addStringOption('o', "operation");
    //CmdLineParser.Option obj_op_csv = obj_parser.addStringOption('c', "csv");
    //CmdLineParser.Option obj_op_field = obj_parser.addStringOption('f', "field");
    //CmdLineParser.Option obj_op_fieldCheck = obj_parser.addStringOption("fieldCheck");
    CmdLineParser.Option obj_op_max = null;

    private ConnectionHandler objConnHandler = null;
    private StatementHandler objStatHandler = null;

    private static Logger logger = Logger.getLogger(MainRun.class.getName());


    /**
     * @param a_args
     */
    public static void main(String[] a_args) {
        // Local variables
        int             iReturn;

        // Initialization
        iReturn = ConstGlobal.PROCESS_EXIT_SUCCESS;
        GlobalVar.getInstance().sProgName = "sqlExecutor.csv";
        GlobalVar.getInstance().sVersionBuild = "21";
        GlobalVar.getInstance().sAuthor = "stupica.com - Bostjan Stupica";

        // Generate main program class
        objInstance = new MainRun();
        iReturn = objInstance.invokeApp(a_args);

        // Return
        if (iReturn != ConstGlobal.PROCESS_EXIT_SUCCESS)
            System.exit(iReturn);
        else
            //System.exit(ConstGlobal.EXIT_SUCCESS);
            return;
    }


    protected void printUsage() {
        super.printUsage();
        //System.err.println("Usage: prog [-h,--help]");
        //System.err.println("            [-q,--quiet]");
        //System.err.println("            [{-c,--currpair} a_currency_pair]");
        System.err.println("            [{-s,--sql} a_sql_statement]");
        System.err.println("            [{-d,--dest} a_jdbc_url]");
        System.err.println("            [{-u,--user} a_jdbc_username]");
        System.err.println("            [{-p,--psw} a_jdbc_password]");
        //System.err.println("            [{-t,--table} table]");
        //System.err.println("            [{-o,--operation} INSERT/UPDATE; def: INSERT]");
        //System.err.println("            [{-c,--csv} CSV_delimiter]");
        //System.err.println("            [{-f,--field(s)} field(s) definition");
        //System.err.println("            [{--fieldCheck} field(s) definition 2 check");
        System.err.println("            [{-m,--maxRows} max rows to import");
    }

    /**
     * Method: initialize
     *
     * ..
     */
    protected void initialize() {
        super.initialize();
        bShouldReadConfig = false;
    }


    /**
     * Method: defineArguments
     *
     * ..
     *
     * @return int iResult	1 = AllOK;
     */
    protected int defineArguments() {
        // Local variables
        int         iResult;

        // Initialization
        iResult = super.defineArguments();

        obj_op_source = obj_parser.addStringOption('s', "sql");
        obj_op_dest = obj_parser.addStringOption('d', "dest");
        obj_op_user = obj_parser.addStringOption('u', "user");
        obj_op_psw = obj_parser.addStringOption('p', "psw");
        obj_op_max = obj_parser.addLongOption('m', "maxRows");

        return iResult;
    }

    /**
     * Method: readArguments
     *
     * ..
     *
     * @return int iResult	1 = AllOK;
     */
    protected int readArguments() {
        // Local variables
        int         iResult;

        // Initialization
        iResult = super.readArguments();

        // Check previous step
        if (iResult == ConstGlobal.RETURN_OK) {
            // Set program parameter
            objInstance.sInputSql = (String)obj_parser.getOptionValue(obj_op_source, "");
        }
        // Check previous step
        if (iResult == ConstGlobal.RETURN_OK) {
            // Set program parameter
            objInstance.sJdbcConn = (String)obj_parser.getOptionValue(obj_op_dest, objInstance.sJdbcConn);
            objInstance.sJdbcUser = (String)obj_parser.getOptionValue(obj_op_user, "test");
            objInstance.sJdbcPsw = (String)obj_parser.getOptionValue(obj_op_psw, "");
        }

        // Check previous step
        if (iResult == ConstGlobal.RETURN_OK) {
            // Set program parameter
            objInstance.iMaxNumOfRows = (Long)obj_parser.getOptionValue(obj_op_max, 0L);
        }
        return iResult;
    }


    /**
     * Method: run
     *
     * Run ..
     *
     * @return int	1 = AllOK;
     */
    public int run() {
        // Local variables
        int         iResult;

        // Initialization
        iResult = ConstGlobal.RETURN_SUCCESS;

        // Open IN file ..
        //
        // Check previous step
        if (iResult == ConstGlobal.RETURN_OK) {
            if (sInputSql == null) {
                iResult = ConstGlobal.RETURN_ERROR;
                logger.severe("run(): Error at input verification - input NOT defined!"
                        + " SqlIN: " + sInputSql);
            }
        }
        // Check previous step
        if (iResult == ConstGlobal.RETURN_OK) {
            if (sInputSql.isEmpty()) {
                iResult = ConstGlobal.RETURN_ERROR;
                logger.severe("run(): Error at input verification - input NOT defined!"
                        + " SqlIN: " + sInputSql);
            }
        }

        // Check previous step
        if (iResult == ConstGlobal.RETURN_OK) {
            objConnHandler = new ConnectionHandler();
            objConnHandler.setDbParam(sJdbcConn, null, sJdbcUser, sJdbcPsw);
            objStatHandler = new StatementHandler();
            objStatHandler.setConnection(objConnHandler);

            iResult = objConnHandler.connect2db();
            // Error
            if (iResult != ConstGlobal.RETURN_OK) {
                logger.severe("run(): Error at connect2db() operation!");
            }
        }

        // Check previous step
        if (iResult == ConstGlobal.RETURN_OK) {
            // Run ..
            iResult = process();
            if (iResult != ConstGlobal.RETURN_OK) {
                logger.severe("run(): Error at process() operation!");
            }
        }
        // Return
        return iResult;
    }


    /**
     * Method: process
     *
     * Run ..
     *
     * @return int i_result	1 = AllOK;
     */
    public int process() {
        // Local variables
        int         iResult;
        //
        long        iCountData = 0L;
        Date        dtStart;
        Date        dtStop = null;

        // Initialization
        iResult = ConstGlobal.RETURN_SUCCESS;
        dtStart = new Date();

        // Process data/SQL ..
        while (iResult == ConstGlobal.RETURN_OK) {
            iCountData++;

            // Check previous step
            if (iResult == ConstGlobal.RETURN_OK) {
                if (bIsModeVerbose) {
                    logger.info("process(): Count/LineNum.: " + iCountData);
                }

                if (sInputSql.toLowerCase().trim().startsWith("select")) {
                    iResult = processSelect();
                } else {
                    iResult = processUID();
                }
                // Error ..
                if (iResult != ConstGlobal.RETURN_OK) {
                    logger.severe("process(): Error at processSelect()/processUID() operation!");
                    break;
                }
            }

            if (iMaxNumOfRows > 0) {
                if (iMaxNumOfRows <= (iCountData - 0)) {
                    logger.info("process(): Maximum number of rows reached: " + iMaxNumOfRows);
                    break;
                }
            }
            break;
        }
        if (iResult == ConstGlobal.RETURN_ENDOFDATA) {
            iResult = ConstGlobal.RETURN_SUCCESS;
        }

        dtStop = new Date();
        logger.info("process(): Processing done."
                + "\n\tData num.: " + iCountData
                + "\n\tDuration(ms): " + (dtStop.getTime() - dtStart.getTime()));
        // Return
        return iResult;
    }


    /**
     * Method: processSelect
     *
     * Process DML operation: Select
     *
     * @return int i_result	1 = AllOK;
     */
    private int processSelect() {
        // Local variables
        int         iResult;
        long        iCountProcessed = 0L;

        // Initialization
        iResult = ConstGlobal.RETURN_SUCCESS;


        iResult = ConstGlobal.RETURN_INVALID;
        // Return
        return iResult;
    }

    /**
     * Method: processUID
     *
     * Process DML operation: Update/Insert/Delete
     *
     * @return int i_result	1 = AllOK;
     */
    private int processUID() {
        // Local variables
        int         iResult;
        long        iCountProcessed;

        // Initialization
        iResult = ConstGlobal.RETURN_SUCCESS;

        iCountProcessed = objStatHandler.executeUpdate(sInputSql);
        // Error ..
        if (iCountProcessed < 0) {
            if (iCountProcessed == ConstGlobal.RETURN_NODATA) {
                iCountProcessed = 0;
            } else {
                iResult = (int) iCountProcessed;
            }
        }
        if (iResult != ConstGlobal.RETURN_OK) {
            logger.severe("processUID(): Error at executeUpdate() operation!"
                    + " CountProcessed: " + iCountProcessed);
        }
        logger.info("processUID(): Processing done."
                + "\tData (rows) processed: " + iCountProcessed);
        // Return
        return iResult;
    }
}
