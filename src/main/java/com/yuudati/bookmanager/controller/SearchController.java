package com.yuudati.bookmanager.controller;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yuudati.bookmanager.entity.*;
import com.yuudati.bookmanager.mapper.BookAttributesMapper;
import com.yuudati.bookmanager.mapper.BookCharactersMapper;
import com.yuudati.bookmanager.mapper.BookInfoMapper;
import com.yuudati.bookmanager.util.SpringContext;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/14 11:31
 */
@Data
@Slf4j
public class SearchController implements Initializable {


    public Button searchButton;
    private MainController mainController;

    private BookInfoMapper infoMapper = (BookInfoMapper) SpringContext.getBean(BookInfoMapper.class);
    private BookAttributesMapper attributesMapper = (BookAttributesMapper) SpringContext.getBean(BookAttributesMapper.class);
    private BookCharactersMapper charactersMapper = (BookCharactersMapper) SpringContext.getBean(BookCharactersMapper.class);

    /**
     * 逗号分割器
     */
    private final Splitter commaSplitter = Splitter.on(",").trimResults().omitEmptyStrings();
    /**
     * 空格分割器
     */
    private final Splitter spaceSplitter = Splitter.on(" ").trimResults().omitEmptyStrings();

    @FXML
    private AnchorPane parentPane;
    /**
     * 仓库路径按钮
     */
    @FXML
    public Button sourcePathButton;
    /**
     * 来源文本框
     */
    @FXML
    public TextField sourcePathTextField;
    /**
     * 初始化仓库按钮
     */
    @FXML
    public Button initDBButton;
    /**
     * 来源/展会组合框
     */
    @FXML
    public ComboBox<String> exhibitionComboBox;
    /**
     * 本名文本框
     */
    @FXML
    public TextField bookNameTextField;
    /**
     * 作者组合框
     */
    @FXML
    public ComboBox<String> artistComboBox;
    /**
     * 角色文本框
     */
    @FXML
    public TextField characterTextField;
    /**
     * 题材组合框
     */
    @FXML
    public ComboBox<String> parodyComboBox;
    /**
     * 汉化组文本框
     */
    @FXML
    public TextField translateTextField;
    /**
     * 属性组合框
     */
    @FXML
    public TextField attributesTextField;
    /**
     * 数据展示表格
     */
    @FXML
    private TableView<BookInfo> dataTableView;
    @FXML
    public TableColumn<BookInfo, String> rowNumCol, exhibitionCol, artistCol, nameCol,
            parodyCol, translateCol, attributesCol, charactersCol;
    @FXML
    public TableColumn<BookInfo, Button> actionCol;

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataTableView.prefWidthProperty().bind(parentPane.widthProperty());
        dataTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        initDataTableView();
        initSearch();
        initData();
    }

    /**
     * 初始化搜索功能
     */
    private void initSearch() {
        // 初始化来源
        List<String> exhibitionList = infoMapper.selectAllExhibition();
        addFilteredList(exhibitionList, exhibitionComboBox);
        // 初始化作者
        List<String> artistList = infoMapper.selectAllArtist();
        addFilteredList(artistList, artistComboBox);
        // 初始化题材
        List<String> parodyList = infoMapper.selectAllParody();
        addFilteredList(parodyList, parodyComboBox);
        searchButton.setOnMouseClicked(event -> doSearch());
        initDBButton.setOnMouseClicked(event -> mainController.showAlert("该功能暂不可用", false));
    }

    /**
     * 执行搜索
     */
    private void doSearch() {
        // 搜索条件
        final String exhibitionFilter = exhibitionComboBox.getValue();
        final String bookNameFilter = bookNameTextField.getText();
        final String artistFilter = artistComboBox.getValue();
        final String parodyFilter = parodyComboBox.getValue();
        final String translateFilter = translateTextField.getText();
        final Iterable<String> charactersFilter = commaSplitter.split(characterTextField.getText());

        Set<String> bookIdListFilter = Sets.newHashSet();

        // 符合属性的
        if (!Strings.isNullOrEmpty(attributesTextField.getText())) {
            final Iterable<String> attributesFilter = commaSplitter.split(attributesTextField.getText());
            final BookAttributesExample attributesExample = new BookAttributesExample();
            attributesExample.createCriteria()
                    .andAttributeIn(Lists.newArrayList(attributesFilter));
            final List<BookAttributes> bookAttributes = attributesMapper.selectByExample(attributesExample);
            if (bookAttributes != null && bookAttributes.size() > 0) {
                List<String> attributesIdList = bookAttributes.stream().map(BookAttributes::getBookId).collect(Collectors.toList());
                if (bookIdListFilter.size() > 0) {
                    bookIdListFilter.retainAll(attributesIdList);
                } else {
                    bookIdListFilter.addAll(attributesIdList);
                }
            }
        }
        // 符合角色的
        if (!Strings.isNullOrEmpty(characterTextField.getText())) {
            final BookCharactersExample charactersExample = new BookCharactersExample();
            charactersExample.createCriteria()
                    .andCharacterIn(Lists.newArrayList(charactersFilter));
            final List<BookCharacters> bookCharacters = charactersMapper.selectByExample(charactersExample);
            if (bookCharacters != null && bookCharacters.size() > 0) {
                List<String> charactersIdList = bookCharacters.stream().map(BookCharacters::getBookId).collect(Collectors.toList());
                if (bookIdListFilter.size() > 0) {
                    bookIdListFilter.retainAll(charactersIdList);
                } else {
                    bookIdListFilter.addAll(charactersIdList);
                }
            }
        }
        final BookInfoExample infoExample = new BookInfoExample();
        if (!Strings.isNullOrEmpty(exhibitionFilter)) {
            infoExample.or()
                    .andExhibitionEqualTo(exhibitionFilter.trim());
        }
        if (!Strings.isNullOrEmpty(bookNameFilter)) {
            infoExample.or()
                    .andNameLike("%" + bookNameFilter.trim() + "%");
        }
        if (!Strings.isNullOrEmpty(artistFilter)) {
            infoExample.or()
                    .andArtistEqualTo(artistFilter.trim());
        }
        if (!Strings.isNullOrEmpty(parodyFilter)) {
            infoExample.or()
                    .andParodyEqualTo(parodyFilter.trim());
        }
        if (!Strings.isNullOrEmpty(translateFilter)) {
            infoExample.or()
                    .andTranslateLike("%" + translateFilter.trim() + "%");
        }
        final List<BookInfo> bookInfos = infoMapper.selectByExample(infoExample);
        if (bookInfos != null && bookInfos.size() > 0) {
            if (bookIdListFilter.size() > 0) {
                final List<BookInfo> collect = bookInfos.stream().filter(item -> bookIdListFilter.contains(item.getId())).collect(Collectors.toList());
                if (collect.size() > 0) {
                    addCharacterAndAttributes(bookInfos);
                    dataTableView.setItems(FXCollections.observableArrayList(collect));
                } else {
                    mainController.showAlert("未找到匹配项", false);
                }
            } else {
                addCharacterAndAttributes(bookInfos);
                dataTableView.setItems(FXCollections.observableArrayList(bookInfos));
            }
        } else {
            mainController.showAlert("未找到匹配项", false);
        }
    }

    /**
     * 选择框添加匹配功能
     *
     * @param valueList
     * @param comboBox
     */
    private void addFilteredList(List<String> valueList, ComboBox<String> comboBox) {
        FilteredList<String> filteredItems = new FilteredList<>(
                FXCollections.observableArrayList(valueList), p -> true);
        comboBox.getEditor().textProperty().addListener(((observable, oldValue, newValue) -> {
            final TextField editor = comboBox.getEditor();
            final String selected = comboBox.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                comboBox.show();
                if (selected == null || !selected.equalsIgnoreCase(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        final Iterable<String> split = spaceSplitter.split(newValue);
                        for (String keyword :
                                split) {
                            if (!item.toUpperCase().contains(keyword.toUpperCase())) {
                                return false;
                            }
                        }
                        return true;
                    });
                }
            });
        }));
        comboBox.setItems(filteredItems);
    }

    /**
     * 初始化表格
     */
    private void initDataTableView() {
        dataTableView.setEditable(true);
        dataTableView.setOnKeyReleased(event -> {
            List<BookInfo> selectedItems = dataTableView.getSelectionModel().getSelectedItems();
            if (selectedItems != null && selectedItems.size() > 0) {
                boolean flag = (event.getCode() == KeyCode.DELETE) && mainController.showAlert("是否要删除所选: " + selectedItems.size() + " 项", true);
                if (flag) {
                    final BookCharactersExample charactersExample = new BookCharactersExample();
                    final List<String> bookIdList = selectedItems.stream().map(BookInfo::getId).collect(Collectors.toList());
                    charactersExample.createCriteria()
                            .andBookIdIn(bookIdList);
                    charactersMapper.deleteByExample(charactersExample);
                    final BookAttributesExample attributesExample = new BookAttributesExample();
                    attributesExample.createCriteria()
                            .andBookIdIn(bookIdList);
                    attributesMapper.deleteByExample(attributesExample);
                    final BookInfoExample infoExample = new BookInfoExample();
                    infoExample.createCriteria()
                            .andIdIn(bookIdList);
                    infoMapper.deleteByExample(infoExample);
                    dataTableView.getItems().removeAll(selectedItems);
                }
            }
        });
        // 行号
        rowNumCol.setCellFactory(column -> new TableCell<BookInfo, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(null);
                this.setGraphic(null);
                if (!empty) {
                    int rowIndex = this.getIndex() + 1;
                    this.setText(String.valueOf(rowIndex));
                }
            }
        });
        exhibitionCol.setCellValueFactory(cellData -> cellData.getValue().exhibitionProperty());
        exhibitionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        exhibitionCol.setOnEditCommit(event -> {
            BookInfo bookInfo = dataTableView.getSelectionModel().getSelectedItem();
            final String newValue = event.getNewValue();
            if (!Strings.isNullOrEmpty(newValue)) {
                bookInfo.setExhibition(newValue.trim());
                final int i = infoMapper.updateByPrimaryKey(bookInfo);
                if (i < 1) {
                    log.info("update book exhibition failed. id: <?>", bookInfo.getId());
                }
            }
        });
        artistCol.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        artistCol.setCellFactory(TextFieldTableCell.forTableColumn());
        artistCol.setOnEditCommit(event -> {
            BookInfo bookInfo = dataTableView.getSelectionModel().getSelectedItem();
            final String newValue = event.getNewValue();
            if (!Strings.isNullOrEmpty(newValue)) {
                bookInfo.setArtist(newValue.trim());
                final int i = infoMapper.updateByPrimaryKey(bookInfo);
                if (i < 1) {
                    log.info("update book artist failed. id: <?>", bookInfo.getId());
                }
            }
        });
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(event -> {
            BookInfo bookInfo = dataTableView.getSelectionModel().getSelectedItem();
            final String newValue = event.getNewValue();
            if (!Strings.isNullOrEmpty(newValue)) {
                bookInfo.setName(newValue.trim());
                final int i = infoMapper.updateByPrimaryKey(bookInfo);
                if (i < 1) {
                    log.info("update book name failed. id: <?>", bookInfo.getId());
                }
            }
        });
        parodyCol.setCellValueFactory(cellData -> cellData.getValue().parodyProperty());
        parodyCol.setCellFactory(TextFieldTableCell.forTableColumn());
        parodyCol.setOnEditCommit(event -> {
            BookInfo bookInfo = dataTableView.getSelectionModel().getSelectedItem();
            final String newValue = event.getNewValue();
            if (!Strings.isNullOrEmpty(newValue)) {
                bookInfo.setParody(newValue.trim());
                final int i = infoMapper.updateByPrimaryKey(bookInfo);
                if (i < 1) {
                    log.info("update book parody failed. id: <?>", bookInfo.getId());
                }
            }
        });
        translateCol.setCellValueFactory(cellData -> cellData.getValue().translateProperty());
        translateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        translateCol.setOnEditCommit(event -> {
            BookInfo bookInfo = dataTableView.getSelectionModel().getSelectedItem();
            final String newValue = event.getNewValue();
            if (!Strings.isNullOrEmpty(newValue)) {
                bookInfo.setTranslate(newValue.trim());
                final int i = infoMapper.updateByPrimaryKey(bookInfo);
                if (i < 1) {
                    log.info("update book artist failed. id: <?>", bookInfo.getId());
                }
            }
        });
        charactersCol.setCellValueFactory(cellData -> cellData.getValue().charactersProperty());
        charactersCol.setCellFactory(TextFieldTableCell.forTableColumn());
        charactersCol.setOnEditCommit(event -> {
            BookInfo bookInfo = dataTableView.getSelectionModel().getSelectedItem();
            final String newValue = event.getNewValue();
            if (!Strings.isNullOrEmpty(newValue)) {
                Iterable<String> split = commaSplitter.split(newValue);
                final BookCharactersExample charactersExample = new BookCharactersExample();
                charactersExample.createCriteria()
                        .andBookIdEqualTo(bookInfo.getId());
                charactersMapper.deleteByExample(charactersExample);
                List<BookCharacters> newBookCharacterList = Lists.newArrayList();
                split.forEach(character -> {
                    final BookCharacters record = new BookCharacters(null, bookInfo.getId(), character);
                    charactersMapper.insert(record);
                    newBookCharacterList.add(record);
                });
                bookInfo.setCharacterList(newBookCharacterList);
            }
        });
        attributesCol.setCellValueFactory(cellData -> cellData.getValue().attributesProperty());
        attributesCol.setCellFactory(TextFieldTableCell.forTableColumn());
        attributesCol.setOnEditCommit(event -> {
            BookInfo bookInfo = dataTableView.getSelectionModel().getSelectedItem();
            final String newValue = event.getNewValue();
            if (!Strings.isNullOrEmpty(newValue)) {
                Iterable<String> split = commaSplitter.split(newValue);
                final BookAttributesExample attributesExample = new BookAttributesExample();
                attributesExample.createCriteria()
                        .andBookIdEqualTo(bookInfo.getId());
                attributesMapper.deleteByExample(attributesExample);
                final List<BookAttributes> newAttributeList = Lists.newArrayList();
                split.forEach(character -> {
                    final BookAttributes record = new BookAttributes(null, bookInfo.getId(), character);
                    attributesMapper.insert(record);
                    newAttributeList.add(record);
                });
                bookInfo.setAttributeList(newAttributeList);
            }
        });
        actionCol.setCellFactory(param -> {
            ButtonCell<BookInfo, Button> buttonCell = new ButtonCell<>();
            Button previewButton = (Button) buttonCell.getGraphic();
            previewButton.setOnAction(event -> {
                dataTableView.getSelectionModel().select(buttonCell.getIndex());
                BookInfo selectedItem = dataTableView.getSelectionModel().getSelectedItem();
                try {
                    Desktop.getDesktop().open(new File(selectedItem.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return buttonCell;
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        final List<BookInfo> bookInfos = infoMapper.selectByExample(new BookInfoExample());
        if (bookInfos != null && bookInfos.size() > 0) {
            addCharacterAndAttributes(bookInfos);
        }
        dataTableView.setItems(FXCollections.observableArrayList(bookInfos));
    }

    private void addCharacterAndAttributes(List<BookInfo> bookInfos) {
        bookInfos.forEach(bookInfo -> {
            final BookCharactersExample bookCharactersExample = new BookCharactersExample();
            bookCharactersExample.createCriteria()
                    .andBookIdEqualTo(bookInfo.getId());
            List<BookCharacters> bookCharacters = charactersMapper.selectByExample(bookCharactersExample);
            final BookAttributesExample bookAttributesExample = new BookAttributesExample();
            bookAttributesExample.createCriteria()
                    .andBookIdEqualTo(bookInfo.getId());
            List<BookAttributes> bookAttributes = attributesMapper.selectByExample(bookAttributesExample);
            bookInfo.setCharacterList(bookCharacters);
            bookInfo.setAttributeList(bookAttributes);
        });
    }

}